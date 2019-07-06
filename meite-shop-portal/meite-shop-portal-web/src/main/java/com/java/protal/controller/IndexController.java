package com.java.protal.controller;

import com.java.core.base.Result;
import com.java.core.constants.Constants;
import com.java.core.web.CookieUtils;
import com.java.member.feign.MemberLoginServiceFeign;
import com.java.member.output.UserOutDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: jiangli
 * @Date: 2019/6/11 14:30
 */
@Controller
public class IndexController {
	@Autowired
	private MemberLoginServiceFeign memberLoginServiceFeign;

	private static final String INDEX_FTL = "index";

	@GetMapping("/")
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
		// 1.从cookie取出token
		String token = CookieUtils.getCookieValue(request, Constants.LOGIN_TOKEN_COOKIE_NAME);
		if (StringUtils.isNotEmpty(token)) {
			// 2.调用会员服务接口,查询会员用户信息
			Result<UserOutDTO> result = memberLoginServiceFeign.getInfo(token);
			if (result.getFlag()) {
				UserOutDTO data = result.getData();
				if (data != null) {
					String mobile = data.getMobile();
					//对手机号码脱敏
					String desensMobile = mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
					model.addAttribute("desensMobile", desensMobile);
				}
			}
		}
		return INDEX_FTL;
	}
}