package com.java.mq;

/**
 * @author jiangli
 * @date 2019/7/8 21:55
 */
public interface MQConstants {
	// 添加积分队列
	String ADD_POINT_QUEUE = "add_point_queue";
	// 支付补偿队列
	String RETRY_PAY_QUEUE = "retry_pay_queue";
	// 交换机
	String POINT_EXCHANGE = "pay_exchange";
	// 添加积分队列与交换机的路由key
	String ADD_POINT_ROUTING_KEY = "addPoint";
}
