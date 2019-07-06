package com.java.pay.strategy;

import com.java.pay.entity.PaymentChannelEntity;
import com.java.pay.out.dto.PayMentTransacDTO;

/**
 * @author jiangli
 * @date 2019/7/6 18:38
 * 支付接口共同行为
 */
public interface PayStrategy {

	String toPayHtml(PaymentChannelEntity paymentChannelEntity,PayMentTransacDTO payMentTransacDTO);
}
