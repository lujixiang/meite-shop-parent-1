package com.java.member.feign;

import com.alibaba.fastjson.JSONObject;
import com.java.core.base.Result;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Auther: jiangli
 * @Date: 2019/6/5 15:49
 */
@FeignClient("app-weixin")
public interface VerifyCodeServiceFeign {

	/**
	 * 功能说明:根据手机号码验证码token是否正确
	 *
	 * @return
	 */
	@ApiOperation(value = "根据手机号码验证码token是否正确")
	@PostMapping("/verifyWxCode")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "phone", dataType = "String", required = true, value = "用户手机号码"),
			@ApiImplicitParam(paramType = "query", name = "wxCode", dataType = "String", required = true, value = "微信注册码") })
	Result<JSONObject> verifyWxCode(@RequestParam("phone") String phone,@RequestParam("wxCode") String wxCode);
}