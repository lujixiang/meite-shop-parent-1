package com.java.member.service;

import com.alibaba.fastjson.JSONObject;
import com.java.core.base.Result;
import com.java.member.input.UserLoginInpDTO;
import com.java.member.output.UserOutDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@Api(tags = "用户登陆服务")
public interface MemberLoginService {
	/**
	 * 用户登陆接口
	 *
	 * @param userLoginInpDTO
	 * @return
	 */
	@PostMapping("/login")
	@ApiOperation(value = "会员用户登陆信息接口")
	Result<JSONObject> login(@RequestBody UserLoginInpDTO userLoginInpDTO);

	/**
	 * 根据token查询用户信息
	 *
	 * @param token
	 * @return
	 */
	@GetMapping("/getUserInfo")
	@ApiOperation(value = "根据token查询用户信息")
	Result<UserOutDTO> getInfo(@RequestParam("token") String token);

	/**
	 * 删除登陆token
	 *
	 * @return
	 */
	@PostMapping("/delToken")
	@ApiOperation(value = "删除登陆token")
	Result<JSONObject> delToken(@RequestParam("token") String token);

	/**
	 * SSO认证系统登录接口
	 */
	@PostMapping("/ssoLogin")
	Result<UserOutDTO> ssoLogin(@RequestBody UserLoginInpDTO userLoginInpDTO);
}
