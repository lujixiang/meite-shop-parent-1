package com.java.member.controller;

import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson.JSONObject;
import com.java.core.base.Result;
import com.java.core.web.RandomValidateCodeUtil;
import com.java.member.feign.MemberRegisterServiceFeign;
import com.java.member.input.UserInpDTO;
import com.java.member.vo.RegisterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class RegisterController {
	//注册页面
	private static final String MB_REGISTER_FTL = "member/register";
	//登录页面
	private static final String MB_LOGIN_FTL = "member/login";

	@Autowired
	private MemberRegisterServiceFeign memberRegisterServiceFeign;

	/**
	 * 跳转到注册页面
	 * 
	 * @return
	 */
	@GetMapping("/register.html")
	public String getRegister() {
		return MB_REGISTER_FTL;
	}

	/**
	 * 接收请求参数
	 * 
	 * @return
	 */
	@PostMapping("/register.html")
	public String postRegister(@ModelAttribute("registerVo") @Valid RegisterVo registerVo, BindingResult bindingResult, Model model, HttpSession httpSession) {
		//参数校验
		if (bindingResult.hasErrors()) {
			// 获取第一个错误!
			String errorMsg = bindingResult.getFieldError().getDefaultMessage();
			model.addAttribute("error", errorMsg);
			return MB_REGISTER_FTL;
		}
		//验证图形验证码是否正确
		Boolean checkVerify = RandomValidateCodeUtil.checkVerify(registerVo.getGraphicCode(), httpSession);
		if (!checkVerify) {
			model.addAttribute("error", "图形验证码不正确!");
			return MB_REGISTER_FTL;
		}
		//调用会员服务注册
		UserInpDTO userInpDTO = Convert.convert(UserInpDTO.class, registerVo);
		Result<JSONObject> result = memberRegisterServiceFeign.register(userInpDTO);
		if (!result.getFlag()) {
			model.addAttribute("error", result.getMsg());
			return MB_REGISTER_FTL;
		}
		return MB_LOGIN_FTL;
	}

}