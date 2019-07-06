package com.java.controller;

import com.java.core.base.Result;
import com.java.feign.PayMentTransacInfoFeign;
import com.java.feign.PaymentChannelFeign;
import com.java.pay.out.dto.PayMentTransacDTO;
import com.java.pay.out.dto.PaymentChannelDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class PayController {
	/**
	 * 500页面
	 */
	private static final String ERROR_500_FTL = "500.ftl";

	@Autowired
	private PayMentTransacInfoFeign payMentTransacInfoFeign;
	@Autowired
	private PaymentChannelFeign paymentChannelFeign;

	@RequestMapping("/pay")
	public String pay(String payToken, Model model) {
		// 1.验证payToken参数
		if (StringUtils.isEmpty(payToken)) {
			model.addAttribute("error", "支付令牌不能为空!");
			return ERROR_500_FTL;
		}
		// 2.使用payToken查询支付信息
		Result<PayMentTransacDTO> result = payMentTransacInfoFeign.tokenByPayMentTransac(payToken);
		if (!result.getFlag()) {
			model.addAttribute("error", result.getMsg());
			return ERROR_500_FTL;
		}
		// 3.查询支付信息
		PayMentTransacDTO data = result.getData();
		model.addAttribute("data", data);
		// 4.查询渠道信息
		List<PaymentChannelDTO> paymentChanneList = paymentChannelFeign.selectAll();
		model.addAttribute("paymentChanneList", paymentChanneList);
		return "index";
	}

}
