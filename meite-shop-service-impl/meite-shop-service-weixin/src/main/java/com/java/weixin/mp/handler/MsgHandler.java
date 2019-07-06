package com.java.weixin.mp.handler;


import com.java.core.base.Result;
import com.java.core.constants.Constants;
import com.java.core.constants.StatusCode;
import com.java.core.utils.RedisUtil;
import com.java.core.utils.RegexUtils;
import com.java.member.output.UserOutDTO;
import com.java.weixin.feign.MemberRegisterServiceFeign;
import com.java.weixin.mp.builder.TextBuilder;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

import static me.chanjar.weixin.common.api.WxConsts.XmlMsgType;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@Component
@Slf4j
public class MsgHandler extends AbstractHandler {
	// 用户发送手机验证码提示
	@Value("${weixin.registration.code.message}")
	private String registrationCodeMessage;
	// 默认用户发送验证码提示
	@Value("${weixin.default.registration.code.message}")
	private String defaultRegistrationCodeMessage;
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private MemberRegisterServiceFeign memberRegisterServiceFeign;

	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService weixinService,
			WxSessionManager sessionManager) {

		if (!wxMessage.getMsgType().equals(XmlMsgType.EVENT)) {
			// TODO 可以选择将消息保存到本地
		}

		// 当用户输入关键词如“你好”，“客服”等，并且有客服在线时，把消息转发给在线客服
		try {
			if (StringUtils.startsWithAny(wxMessage.getContent(), "你好", "客服")
					&& weixinService.getKefuService().kfOnlineList().getKfOnlineList().size() > 0) {
				return WxMpXmlOutMessage.TRANSFER_CUSTOMER_SERVICE().fromUser(wxMessage.getToUser())
						.toUser(wxMessage.getFromUser()).build();
			}
		} catch (WxErrorException e) {
			e.printStackTrace();
		}
		//1.获取微信客户端发送的消息
		String userMessage = wxMessage.getContent();
		//2.判断微信客户端发送的消息是否为手机号码
		if (RegexUtils.checkMobile(userMessage)) {
			//调用会员服务验证手机号码是否存在
			Result<UserOutDTO> result = memberRegisterServiceFeign.existMobile(userMessage);
			if (result.getFlag()) {
				return new TextBuilder().build("手机号码"+userMessage+"已经注册!", wxMessage, weixinService);
			}
			//返回的不是用户不存在,都直接返回
			if (!result.getCode().equals(StatusCode.ERROR)) {
				return new TextBuilder().build(result.getMsg(), wxMessage, weixinService);
			}
			//3.生成随机4位的数字验证码
			String code = RandomStringUtils.randomNumeric(4);
			//4.将验证码存入redis
			redisUtil.setString(Constants.WEIXINCODE_KEY+userMessage,code,Constants.WEIXINCODE_TIMEOUT);
			//5.替换
			String content = String.format(registrationCodeMessage,code);
			return new TextBuilder().build(content, wxMessage, weixinService);
		}
//		 String content = "收到信息内容：" + JsonUtils.toJson(wxMessage);
		//返回默认消息(扩展:调用第三方机器人接口)
		return new TextBuilder().build(defaultRegistrationCodeMessage, wxMessage, weixinService);

	}

}
