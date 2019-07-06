package com.java.member.service;

import com.alibaba.fastjson.JSONObject;
import com.java.core.base.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Api(tags = "QQ登录服务")
public interface QQAuthService {

	@ApiOperation(value = "使用openId查询用户")
	@GetMapping("/findByOpenId")
	Result<JSONObject> findByOpenId(@RequestParam("openId") String openId);
}
