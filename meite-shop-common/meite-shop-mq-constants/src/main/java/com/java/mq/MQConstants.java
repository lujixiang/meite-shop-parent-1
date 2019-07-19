package com.java.mq;

/**
 * @author jiangli
 * @date 2019/7/8 21:55
 */
public interface MQConstants {
	/*支付相关*/
	// 添加积分队列
	String ADD_POINT_QUEUE = "add_point_queue";
	// 支付补偿队列
	String RETRY_PAY_QUEUE = "retry_pay_queue";
	// 交换机
	String POINT_EXCHANGE = "pay_exchange";
	// 添加积分队列与交换机的路由key
	String ADD_POINT_ROUTING_KEY = "addPoint";



	/*秒杀相关*/
	// 修改秒杀商品库存队列
	String UPDATE_STOCK_QUEUE = "update_stock_queue";
	// 秒杀交换机
	String SECKILL_EXCHANGE = "seckill_exchange";
	// 修改秒杀商品库存队列与秒杀交换机的路由key
	String UPDATE_STOCK_ROUTING_KEY = "update_stock";
}
