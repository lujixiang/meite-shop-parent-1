package com.java.pay.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.java.pay.dao.PaymentChannelDao;
import com.java.pay.entity.PaymentChannelEntity;
import com.java.pay.out.dto.PaymentChannelDTO;
import com.java.pay.service.PaymentChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PaymentChannelServiceImpl implements PaymentChannelService {
	@Autowired
	private PaymentChannelDao paymentChannelDao;

	@Override
	public List<PaymentChannelDTO> selectAll() {
		List<PaymentChannelEntity> paymentChanneList = paymentChannelDao.selectList(new LambdaQueryWrapper<PaymentChannelEntity>().eq(PaymentChannelEntity::getChannelState,0));
		return Convert.toList(PaymentChannelDTO.class,paymentChanneList);
	}

}
