package com.java.pay.config;

import com.java.mq.MQConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Rabbitmq 关联队列相关配置
 */
@Component
public class RabbitmqConfig {
	// 1.添加积分队列
	@Bean
	public Queue directIntegralDicQueue() {
		return new Queue(MQConstants.ADD_POINT_QUEUE);
	}

	// 2.定义支付补偿队列
	@Bean
	public Queue directCreateintegralQueue() {
		return new Queue(MQConstants.RETRY_PAY_QUEUE);
	}

	// 2.定义交换机
	@Bean
	DirectExchange directintegralExchange() {
		return new DirectExchange(MQConstants.POINT_EXCHANGE);
	}

	// 3.积分队列与交换机绑定
	@Bean
	Binding bindingExchangeintegralDicQueue() {
		return BindingBuilder.bind(directIntegralDicQueue()).to(directintegralExchange()).with(MQConstants.ADD_POINT_ROUTING_KEY);
	}

	// 3.补偿队列与交换机绑定
	@Bean
	Binding bindingExchangeCreateintegral() {
		return BindingBuilder.bind(directCreateintegralQueue()).to(directintegralExchange()).with(MQConstants.ADD_POINT_ROUTING_KEY);
	}

}
