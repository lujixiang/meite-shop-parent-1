package com.java.pay.service.impl;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.java.core.base.Result;
import com.java.pay.dao.PaymentChannelDao;
import com.java.pay.entity.PaymentChannelEntity;
import com.java.pay.factory.StrategyFactory;
import com.java.pay.out.dto.PayMentTransacDTO;
import com.java.pay.service.PayContextService;
import com.java.pay.service.PayMentTransacInfoService;
import com.java.pay.strategy.PayStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jiangli
 * @date 2019/7/6 18:29
 */
@RestController
public class PayContextServiceImpl implements PayContextService {
	@Autowired
	private PaymentChannelDao paymentChannelDao;
	@Autowired
	private PayMentTransacInfoService payMentTransacInfoService;

	@Override
	public Result<JSONObject> toPayHtml(String channelId, String payToken) {
		//1.获取渠道信息
		PaymentChannelEntity paymentChannelEntity = paymentChannelDao.selectOne(new LambdaQueryWrapper<PaymentChannelEntity>().eq(PaymentChannelEntity::getChannelId, channelId)
				.eq(PaymentChannelEntity::getChannelState, 0));
		if (paymentChannelEntity == null) {
			return Result.error("没有查询到渠道信息");
		}
		//2.获取支付参数
		Result<PayMentTransacDTO> result = payMentTransacInfoService.tokenByPayMentTransac(payToken);
		if (!result.getFlag()) {
			return Result.error(result.getMsg());
		}
		PayMentTransacDTO payMentTransacDTO = result.getData();
		//3.执行具体的支付渠道方法获取html表单数据
		String classAddress = paymentChannelEntity.getClassAddress();
		PayStrategy payStrategy = StrategyFactory.getPayStrategy(classAddress);
		if (payStrategy == null) {
			return Result.error("没有查询到支付实现类");
		}
		String payHtml = payStrategy.toPayHtml(paymentChannelEntity, payMentTransacDTO);
		//4.返回html
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("payHtml",payHtml);
		return Result.ok(jsonObject);
	}
}
