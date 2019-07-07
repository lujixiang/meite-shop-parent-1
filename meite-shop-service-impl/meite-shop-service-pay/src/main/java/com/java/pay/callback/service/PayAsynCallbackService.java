package com.java.pay.callback.service;

import com.java.pay.callback.template.AbstractPayCallbackTemplate;
import com.java.pay.callback.template.factory.TemplateFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class PayAsynCallbackService {
	private static final String UNIONPAYCALLBACK_TEMPLATE = "unionPayCallbackTemplate";
	private static final String ALIPAYCALLBACK_TEMPLATE = "aliPayCallbackTemplate";

	/**
	 * 银联异步回调接口执行代码
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping("/unionPayAsynCallback")
	public String unionPayAsynCallback(HttpServletRequest req, HttpServletResponse resp) {
		AbstractPayCallbackTemplate abstractPayCallbackTemplate = TemplateFactory
				.getPayCallbackTemplate(UNIONPAYCALLBACK_TEMPLATE);
		return abstractPayCallbackTemplate.asyncCallBack(req, resp);
	}

	/**
	 * 支付宝异步回调接口执行代码
	 *
	 * @param req
	 * @param resp
	 * @return
	 */
	@RequestMapping("/aliPayAsynCallback")
	public String aliPayAsynCallback(HttpServletRequest req, HttpServletResponse resp) {
		AbstractPayCallbackTemplate abstractPayCallbackTemplate = TemplateFactory
				.getPayCallbackTemplate(ALIPAYCALLBACK_TEMPLATE);
		return abstractPayCallbackTemplate.asyncCallBack(req, resp);
	}

}
