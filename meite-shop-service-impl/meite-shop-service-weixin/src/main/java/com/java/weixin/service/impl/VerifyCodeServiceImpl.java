package com.java.weixin.service.impl;

import com.java.core.base.Result;
import com.java.core.constants.Constants;
import com.java.core.utils.RedisUtil;
import com.java.core.utils.RegexUtils;
import com.java.weixin.service.VerifyCodeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.fastjson.JSONObject;

@RestController
public class VerifyCodeServiceImpl extends Result<JSONObject> implements VerifyCodeService {

	@Autowired
	private RedisUtil redisUtil;

	@Override
	public Result<JSONObject> verifyWxCode(String phone, String wxCode) {
		// 1.验证码参数是否为空
		if (StringUtils.isEmpty(phone) || !RegexUtils.checkMobile(phone)) {
			return error("手机号码不正确!");
		}
		if (StringUtils.isEmpty(wxCode)) {
			return error("注册码不能为空!");
		}
		// 2.根据手机号码查询redis返回对应的注册码
		String wxCodeKey = Constants.WEIXINCODE_KEY + phone;
		String redisCode = redisUtil.getString(wxCodeKey);
		if (StringUtils.isEmpty(redisCode)) {
			return error("注册码过期!!");
		}
		// 3.redis中的注册码与传递参数的wxCode进行比对
		if (!redisCode.equals(wxCode)) {
			return error("注册码不正确");
		}
		// 4.删除redis
		redisUtil.delKey(wxCodeKey);
		return ok("验证码正确");
	}

}
