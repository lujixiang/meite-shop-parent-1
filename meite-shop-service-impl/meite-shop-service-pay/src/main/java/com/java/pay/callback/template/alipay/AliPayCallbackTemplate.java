package com.java.pay.callback.template.alipay;

import com.java.pay.callback.template.AbstractPayCallbackTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 支付宝支付回调模版实现
 */
@Component
public class AliPayCallbackTemplate extends AbstractPayCallbackTemplate {
	@Override
	public Map<String, String> verifySignature(HttpServletRequest req, HttpServletResponse resp) {
		return null;
	}

	@Override
	public String asyncService(Map<String, String> verifySignature) {
		return null;
	}

	@Override
	public String failResult() {
		return "FAILED";
	}

	@Override
	public String successResult() {
		return "SUCCESS";
	}
}
