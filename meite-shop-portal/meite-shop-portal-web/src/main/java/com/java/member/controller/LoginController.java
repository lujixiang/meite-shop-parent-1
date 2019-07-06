package com.java.member.controller;

import cn.hutool.core.convert.Convert;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.alibaba.fastjson.JSONObject;
import com.java.core.base.Result;
import com.java.core.constants.Constants;
import com.java.core.web.CookieUtils;
import com.java.core.web.RandomValidateCodeUtil;
import com.java.core.web.WebBrowserInfoUtil;
import com.java.member.feign.MemberLoginServiceFeign;
import com.java.member.input.UserLoginInpDTO;
import com.java.member.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class LoginController {
	/**
	 * 登录页面
	 */
	private static final String MB_LOGIN_FTL = "member/login";
	/**
	 * 重定向到首页
	 */
	private static final String REDIRECT_INDEX = "redirect:/";

	@Autowired
	private MemberLoginServiceFeign memberLoginServiceFeign;

	/**
	 * 跳转到登录页面
	 *
	 * @return
	 */
	@GetMapping("/login.html")
	public String getLogin() {
		return MB_LOGIN_FTL;
	}

	/**
	 * 登录
	 *
	 * @return
	 */
	@PostMapping("/login.html")
	public String postLogin(@ModelAttribute("loginVo") @Valid LoginVo loginVo, BindingResult bindingResult, Model model, HttpServletRequest request,
	                        HttpServletResponse response, HttpSession httpSession) {
		//参数校验
		if (bindingResult.hasErrors()) {
			// 获取第一个错误!
			String errorMsg = bindingResult.getFieldError().getDefaultMessage();
			model.addAttribute("error", errorMsg);
			return MB_LOGIN_FTL;
		}
		//校验图形验证码
		String graphicCode = loginVo.getGraphicCode();
		if (!RandomValidateCodeUtil.checkVerify(graphicCode, httpSession)) {
			model.addAttribute("error", "图形验证码不正确!");
			return MB_LOGIN_FTL;
		}
		//调用会员服务登录
		UserLoginInpDTO userLoginInpDTO = Convert.convert(UserLoginInpDTO.class, loginVo);
		userLoginInpDTO.setLoginType(Constants.MEMBER_LOGIN_TYPE_PC);
		userLoginInpDTO.setDeviceInfo(WebBrowserInfoUtil.webBrowserInfo(request));
		Result<JSONObject> result = memberLoginServiceFeign.login(userLoginInpDTO);
		if (!result.getFlag()) {
			model.addAttribute("error", result.getMsg());
			return MB_LOGIN_FTL;
		}
		//登录成功后将token存入cookie,首页读取cookie,查询用户信息返回到页面展示
		JSONObject data = result.getData();
		String token = data.getString("token");
		CookieUtils.setCookie(request, response, Constants.LOGIN_TOKEN_COOKIE_NAME, token);
		return REDIRECT_INDEX;
	}

	/**
	 * 退出
	 *
	 * @param request
	 * @return
	 */
	@GetMapping("/exit")
	public String exit(HttpServletRequest request,HttpServletResponse response) {
		// 1. 从cookie中获取token
		String token = CookieUtils.getCookieValue(request, Constants.LOGIN_TOKEN_COOKIE_NAME);
		CookieUtils.deleteCookie(request,response,Constants.LOGIN_TOKEN_COOKIE_NAME);
		memberLoginServiceFeign.delToken(token);
		return REDIRECT_INDEX;
	}
}