package com.java.point.consume;

import java.io.IOException;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.java.mq.MQConstants;
import com.java.point.dao.UserPointDao;
import com.java.point.entity.UserPointEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;

import lombok.extern.slf4j.Slf4j;

/**
 * @description: 积分服务消费者
 */
@Component
@Slf4j
public class PointConsumer {
	@Autowired
	private UserPointDao userPointDao;

	@RabbitListener(queues = MQConstants.ADD_POINT_QUEUE)
	public void process(Message message, @Headers Map<String, Object> headers, Channel channel) throws IOException {
		try {
			String messageId = message.getMessageProperties().getMessageId();
			String msg = new String(message.getBody(), "UTF-8");
			log.info(">>>messageId:{},msg:{}", messageId, msg);
			JSONObject jsonObject = JSONObject.parseObject(msg);
			String paymentId = jsonObject.getString("paymentId");
			if (StringUtils.isEmpty(paymentId)) {
				log.error(">>>>支付id不能为空 paymentId:{}", paymentId);
				basicNack(message, channel);
				return;
			}
			// 使用paymentId查询是否已经增加过积分 网络重试间隔
			UserPointEntity resultIntegralEntity = userPointDao.selectOne(new LambdaQueryWrapper<UserPointEntity>().eq(UserPointEntity::getPaymentId,paymentId));
			if (resultIntegralEntity != null) {
				log.error(">>>>paymentId:{}已经增加过积分", paymentId);
				// 已经增加过积分，通知MQ不要在继续重试。
				basicNack(message, channel);
				return;
			}
			Long userId = jsonObject.getLong("userId");
			if (userId == null) {
				log.error(">>>>paymentId:{},对应的用户userId参数为空", paymentId);
				basicNack(message, channel);
				return;
			}
			Long integral = jsonObject.getLong("integral");
			if (integral == null) {
				log.error(">>>>paymentId:{},对应的用户integral参数为空", integral);
				return;
			}
			UserPointEntity userPointEntity = new UserPointEntity();
			userPointEntity.setPaymentId(paymentId);
			userPointEntity.setPoint(integral);
			userPointEntity.setUserId(userId);
			userPointEntity.setAvailability(1);
			// 插入到数据库中
			int insertIntegral = userPointDao.insert(userPointEntity);
			if (insertIntegral > 0) {
				// 手动签收消息,通知mq服务器端删除该消息
				basicNack(message, channel);
			}
			// 采用重试机制
		} catch (Exception e) {
			log.error(">>>>ERROR MSG:", e.getMessage());
			basicNack(message, channel);
		}

	}

	// 消费者获取到消息之后 手动签收 通知MQ删除该消息
	private void basicNack(Message message, Channel channel) throws IOException {
		channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
	}

	// 什么场景下 适合于重试 网络连接、空指针 参数错误
}
