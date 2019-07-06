package com.xxl.sso.server.feign;

import com.java.core.base.Result;
import com.java.member.input.UserLoginInpDTO;
import com.java.member.output.UserOutDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Auther: jiangli
 * @Date: 2019/6/21 10:28
 */
@FeignClient("app-member")
public interface MemberLoginServiceFeign {

	/**
	 * SSO认证系统登录接口
	 */
	@PostMapping("/ssoLogin")
	Result<UserOutDTO> ssoLogin(@RequestBody UserLoginInpDTO userLoginInpDTO);
}