package com.java.zuul.feign;

import com.alibaba.fastjson.JSONObject;
import com.java.core.base.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("app-auth")
public interface AuthorizationServiceFeign {

	/*
	 * 验证Token是否失效
	 */
	@GetMapping("/getAppInfo")
	public Result<JSONObject> getAppInfo(@RequestParam("accessToken") String accessToken);
}
