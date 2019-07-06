package com.java.member.controller;

import cn.hutool.core.convert.Convert;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.alibaba.fastjson.JSONObject;
import com.java.core.base.Result;
import com.java.core.constants.Constants;
import com.java.core.constants.StatusCode;
import com.java.core.web.CookieUtils;
import com.java.core.web.WebBrowserInfoUtil;
import com.java.member.feign.MemberLoginServiceFeign;
import com.java.member.feign.QQAuthServiceFeign;
import com.java.member.input.UserLoginInpDTO;
import com.java.member.vo.LoginVo;
import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.oauth.Oauth;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Auther: jiangli
 * @Date: 2019/6/17 11:10
 */
@Controller
public class QQLoginController {
	/**
	 * 重定向到首页
	 */
	private static final String REDIRECT_INDEX = "redirect:/";
	/**
	 * 登录页面
	 */
	private static final String MB_LOGIN_FTL = "member/login";
	private static final String MB_QQ_QQLOGIN = "member/qqlogin";
	@Autowired
	private QQAuthServiceFeign qqAuthServiceFeign;
	@Autowired
	private MemberLoginServiceFeign memberLoginServiceFeign;

	/**
	 * 生成授权链接
	 * @param request
	 * @return
	 */
	@GetMapping("/qqAuth")
	public String qqAuth(HttpServletRequest request) {
		try {
			String authorizeURL = new Oauth().getAuthorizeURL(request);
			return "redirect:"+authorizeURL;
		} catch (QQConnectException e) {
			e.printStackTrace();
			return MB_LOGIN_FTL;
		}
	}

	/**
	 * QQ授权回调
	 * @param code
	 * @param request
	 * @return
	 */
	@GetMapping("/notify")
	public String notify(String code, HttpServletRequest request, HttpServletResponse response,HttpSession session) {
		//使用授权码换取accessToken
		try {
			AccessToken accessToken = new Oauth().getAccessTokenByRequest(request);
			if (accessToken == null || StringUtils.isEmpty(accessToken.getAccessToken())) {
				return MB_LOGIN_FTL;
			}
			//使用accessToken获取用户openId
			OpenID openID = new OpenID(accessToken.getAccessToken());
			String openId = openID.getUserOpenID();
			if (StringUtils.isEmpty(openId)) {
				return MB_LOGIN_FTL;
			}
			//通过opendId查询是否已经关联账号信息
			Result<JSONObject> byOpenId = qqAuthServiceFeign.findByOpenId(openId);
			if (byOpenId.getCode().equals(StatusCode.NORESULT)) {
				//获取QQ头像
				UserInfo userInfo = new UserInfo(accessToken.getAccessToken(), openId);
				UserInfoBean userInfoBean = userInfo.getUserInfo();
				String avatarURL100 = userInfoBean.getAvatar().getAvatarURL100();
				request.setAttribute("avatarURL100",avatarURL100);
				session.setAttribute(Constants.LOGIN_QQ_OPENID,openId);
				return MB_QQ_QQLOGIN;
			}
			// 自动实现登陆
			JSONObject data = byOpenId.getData();
			String token = data.getString("token");
			CookieUtils.setCookie(request, response, Constants.LOGIN_TOKEN_COOKIE_NAME, token);
			return REDIRECT_INDEX;
		} catch (QQConnectException e) {
			return MB_LOGIN_FTL;
		}
	}

	@RequestMapping("/qqJointLogin")
	public String qqJointLogin(@ModelAttribute("loginVo") LoginVo loginVo, Model model, HttpServletRequest request,
	                           HttpServletResponse response, HttpSession httpSession) {

		// 1.获取用户openid
		String qqOpenId = (String) httpSession.getAttribute(Constants.LOGIN_QQ_OPENID);

		// 2.将vo转换dto调用会员登陆接口
		UserLoginInpDTO userLoginInpDTO = Convert.convert(UserLoginInpDTO.class,loginVo);
		userLoginInpDTO.setQqOpenId(qqOpenId);
		userLoginInpDTO.setLoginType(Constants.MEMBER_LOGIN_TYPE_PC);
		String info = WebBrowserInfoUtil.webBrowserInfo(request);
		userLoginInpDTO.setDeviceInfo(info);
		Result<JSONObject> login = memberLoginServiceFeign.login(userLoginInpDTO);
		if (!login.getFlag()) {
			return MB_QQ_QQLOGIN;
		}
		// 3.登陆成功之后如何处理 保持会话信息 将token存入到cookie 里面 首页读取cookietoken 查询用户信息返回到页面展示
		JSONObject data = login.getData();
		String token = data.getString("token");
		CookieUtils.setCookie(request, response, Constants.LOGIN_TOKEN_COOKIE_NAME, token);
		return REDIRECT_INDEX;
	}

}