//package com.java.seckill.consumer;
//
//import com.alibaba.fastjson.JSONObject;
//import com.java.mq.MQConstants;
//import com.java.seckill.dao.MeiteOrderDao;
//import com.java.seckill.dao.MeiteSeckillDao;
//import com.java.seckill.entity.MeiteOrderEntity;
//import com.java.seckill.entity.MeiteSeckillEntity;
//import com.rabbitmq.client.Channel;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.handler.annotation.Headers;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.io.IOException;
//import java.util.Map;
//
///**
// * 库存消费者
// */
//@Component
//@Slf4j
//public class StockConsumer {
//	@Autowired
//	private MeiteSeckillDao seckillMapper;
//	@Autowired
//	private MeiteOrderDao orderMapper;
//
//	@RabbitListener(queues = MQConstants.UPDATE_STOCK_QUEUE)
//	@Transactional
//	public void process(Message message, @Headers Map<String, Object> headers, Channel channel) throws IOException {
//		String messageId = message.getMessageProperties().getMessageId();
//		String msg = new String(message.getBody(), "UTF-8");
//		log.info(">>>messageId:{},msg:{}", messageId, msg);
//		JSONObject jsonObject = JSONObject.parseObject(msg);
//		// 1.获取秒杀id
//		Long seckillProductId = jsonObject.getLong("seckillProductId");
//		MeiteSeckillEntity seckillEntity = seckillMapper.selectById(seckillProductId);
//		if (seckillEntity == null) {
//			log.warn("seckillProductId:{},商品信息不存在!", seckillProductId);
//			basicNack(message, channel);
//			return;
//		}
//		Long version = seckillEntity.getVersion();
//		int inventoryDeduction = seckillMapper.updateStock(seckillProductId, version);
//		if (!toDaoResult(inventoryDeduction)) {
//			log.info(">>>seckillId:{}修改库存失败>>>>inventoryDeduction返回为{} 秒杀失败！", seckillProductId, inventoryDeduction);
//			basicNack(message, channel);
//			return;
//		}
//		// 2.添加秒杀订单
//		MeiteOrderEntity orderEntity = new MeiteOrderEntity();
//		String phone = jsonObject.getString("phone");
//		orderEntity.setUserPhone(phone);
//		orderEntity.setSeckillProductId(seckillProductId);
//		orderEntity.setState(1);
//		int insertOrder = orderMapper.insert(orderEntity);
//		if (!toDaoResult(insertOrder)) {
//			basicNack(message, channel);
//			return;
//		}
//		basicNack(message, channel);
//		log.info(">>>修改库存成功seckillId:{}>>>>inventoryDeduction返回为{} 秒杀成功", seckillProductId, inventoryDeduction);
//	}
//
//	// 调用数据库层判断
//	public Boolean toDaoResult(int result) {
//		return result > 0 ? true : false;
//	}
//
//	// 消费者获取到消息之后 手动签收 通知MQ删除该消息
//	private void basicNack(Message message, Channel channel) throws IOException {
//		channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
//	}
//
//
//}
