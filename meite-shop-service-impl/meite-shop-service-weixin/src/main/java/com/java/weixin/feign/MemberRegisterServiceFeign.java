package com.java.weixin.feign;

import com.java.core.base.Result;
import com.java.member.output.UserOutDTO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Auther: jiangli
 * @Date: 2019/6/5 14:19
 */
@FeignClient("app-member")
public interface MemberRegisterServiceFeign {
	/**
	 * 根据手机号码查询是否已经存在,如果存在返回当前用户信息
	 *
	 * @param mobile
	 * @return
	 */
	@ApiOperation(value = "根据手机号码查询是否已经存在")
	@ApiImplicitParams({@ApiImplicitParam(paramType = "query", name = "mobile", dataType = "String", required = true, value = "用户手机号码")})
	@PostMapping("/existMobile") //Feign客户端调用的时候如果有参数的话，默认是发送post请求,参数要加注解
	Result<UserOutDTO> existMobile(@RequestParam("mobile") String mobile);
}