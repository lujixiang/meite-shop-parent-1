package com.java.member.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.java.core.base.Result;
import com.java.member.entity.UserDO;
import com.java.member.input.UserInpDTO;
import com.java.member.output.UserOutDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Api(tags = "会员注册服务")
public interface MemberRegisterService extends IService<UserDO>{
	/**
	 * 用户注册接口
	 *
	 * @param userInpDTO
	 * @return
	 */
	@ApiOperation(value = "会员用户注册信息接口")
	@PostMapping("/register")
	Result<JSONObject> register(@RequestBody UserInpDTO userInpDTO);

	/**
	 * 根据手机号码查询是否已经存在,如果存在返回当前用户信息
	 *
	 * @param mobile
	 * @return
	 */
	@ApiOperation(value = "根据手机号码查询是否已经存在")
	@ApiImplicitParams({@ApiImplicitParam(paramType = "query", name = "mobile", dataType = "String", required = true, value = "用户手机号码")})
	@PostMapping("/existMobile") //Feign客户端调用的时候如果有参数的话，默认是发送post请求
	Result existMobile(@RequestParam("mobile") String mobile);

}