package com.java.pay.strategy.impl;

import com.java.pay.entity.PaymentChannelEntity;
import com.java.pay.out.dto.PayMentTransacDTO;
import com.java.pay.strategy.PayStrategy;

/**
 * @author jiangli
 * @date 2019/7/6 18:40
 */
public class AliPayStrategy implements PayStrategy {
	@Override
	public String toPayHtml(PaymentChannelEntity paymentChannelEntity, PayMentTransacDTO payMentTransacDTO) {
		return "支付宝支付..........";
	}
}
