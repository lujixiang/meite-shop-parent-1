package com.java.member.feign;

import com.alibaba.fastjson.JSONObject;
import com.java.core.base.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("app-member")
public interface QQAuthServiceFeign {

	@ApiOperation(value = "使用openId查询用户")
	@GetMapping("/findByOpenId")
	Result<JSONObject> findByOpenId(@RequestParam("openId") String openId);
}
