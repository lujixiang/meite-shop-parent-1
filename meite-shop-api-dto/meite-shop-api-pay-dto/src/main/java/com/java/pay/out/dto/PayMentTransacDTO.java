package com.java.pay.out.dto;

import java.util.Date;

import lombok.Data;

@Data
public class PayMentTransacDTO {

	/** 主键ID */
	private Long id;
	/** 支付金额 */
	private Long payAmount;
	/** 支付状态;0待支付1支付完成 2支付超时3支付失败 */
	private Integer paymentStatus;
	/** 用户ID */
	private Integer userId;
	/** 订单号码 */
	private String orderId;
	/**
	 * 第三方支付id 支付宝、银联等
	 */
	private String thirdTradeNo;

	/**
	 * 使用雪花算法生产 支付系统 支付id
	 */
	private String paymentId;
	/** 创建时间 */
	private Date createdTime;

}
