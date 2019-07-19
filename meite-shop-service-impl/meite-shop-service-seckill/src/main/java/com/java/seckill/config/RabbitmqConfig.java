package com.java.seckill.config;

import com.java.mq.MQConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Rabbitmq 秒杀队列相关配置
 */
@Component
public class RabbitmqConfig {
	// 1.修改秒杀商品库存队列
	@Bean
	public Queue directUpdateStockQueue() {
		return new Queue(MQConstants.UPDATE_STOCK_QUEUE);
	}

	// 2.定义交换机
	@Bean
	DirectExchange directSeckillExchange() {
		return new DirectExchange(MQConstants.SECKILL_EXCHANGE);
	}

	// 3.修改秒杀商品库存队列与交换机绑定
	@Bean
	Binding bindingExchangeintegralDicQueue() {
		return BindingBuilder.bind(directUpdateStockQueue()).to(directSeckillExchange()).with(MQConstants.UPDATE_STOCK_ROUTING_KEY);
	}

}
