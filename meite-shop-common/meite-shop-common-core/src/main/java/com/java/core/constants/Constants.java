package com.java.core.constants;

/**
 * @Auther: jiangli
 * @Date: 2019/6/6 10:07
 */
public interface Constants {
	//redis微信codeKey
	String WEIXINCODE_KEY = "weixincode_key";
	//redis微信codeKey过期时间 单位:s
	Long WEIXINCODE_TIMEOUT = 1800l;
	// token
	String MEMBER_TOKEN_KEYPREFIX = "member_login";
	// 安卓的登陆类型
	String MEMBER_LOGIN_TYPE_ANDROID = "Android";
	// IOS的登陆类型
	String MEMBER_LOGIN_TYPE_IOS = "IOS";
	// PC的登陆类型
	String MEMBER_LOGIN_TYPE_PC = "PC";
	// 登陆超时时间 有效期 90天
	Long MEMBRE_LOGIN_TOKEN_TIME = 77776000L;

	String LOGIN_TOKEN_COOKIE_NAME = "login.pc.token";

	String LOGIN_QQ_OPENID = "qq_openid";
}