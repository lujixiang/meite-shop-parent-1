package com.java.pay.service;

import cn.hutool.json.JSONObject;
import com.java.core.base.Result;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author jiangli
 * @date 2019/7/6 18:26
 * 根据不同的渠道id返回不同的支付提交表单
 */
public interface PayContextService {

	@GetMapping("/toPayHtml")
	Result<JSONObject> toPayHtml(String channelId,String payToken);
}
