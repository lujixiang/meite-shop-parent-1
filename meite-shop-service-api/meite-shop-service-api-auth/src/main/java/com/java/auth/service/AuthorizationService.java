package com.java.auth.service;

import com.alibaba.fastjson.JSONObject;
import com.java.core.base.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 用户授权接口
 * 
 */
public interface AuthorizationService {
	/**
	 * 机构申请 获取appid 和appsecret
	 * 
	 * @return
	 */
	@GetMapping("/applyAppInfo")
	public Result<JSONObject> applyAppInfo(@RequestParam("appName") String appName);

	/*
	 * 使用appid 和appsecret密钥获取AccessToken
	 */
	@GetMapping("/getAccessToken")
	public Result<JSONObject> getAccessToken(@RequestParam("appId") String appId,
												   @RequestParam("appSecret") String appSecret);

	/*
	 * 验证Token是否失效
	 */
	@GetMapping("/getAppInfo")
	public Result<JSONObject> getAppInfo(@RequestParam("accessToken") String accessToken);

}
