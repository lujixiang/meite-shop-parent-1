package com.java.pay.service;

import com.java.pay.out.dto.PaymentChannelDTO;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface PaymentChannelService {
	/**
	 * 查询所有支付渠道
	 * 
	 * @return
	 */
	@GetMapping("/selectAll")
	List<PaymentChannelDTO> selectAll();
}
