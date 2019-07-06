package com.java.member.feign;

import com.alibaba.fastjson.JSONObject;
import com.java.core.base.Result;
import com.java.member.input.UserInpDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("app-member")
public interface MemberRegisterServiceFeign {
	/**
	 * 用户注册接口
	 *
	 * @param userInpDTO
	 * @return
	 */
	@ApiOperation(value = "会员用户注册信息接口")
	@PostMapping("/register")
	Result<JSONObject> register(@RequestBody UserInpDTO userInpDTO);

}
