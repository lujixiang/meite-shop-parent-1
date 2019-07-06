package com.java.pay.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 支付交易日志表 
 * 
 * @author jiangli
 * @email 31346337@qq.com
 * @date 2019-07-04 11:36:14
 */
@Data
@TableName("payment_transaction_log")
public class PaymentTransactionLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@TableId
	private Integer id;
	/**
	 * 同步回调日志
	 */
	private String synchLog;
	/**
	 * 异步回调日志
	 */
	private String asyncLog;
	/**
	 * 支付渠道ID
	 */
	private Integer channelId;
	/**
	 * 支付交易ID
	 */
	private Integer transactionId;
	/**
	 * 乐观锁
	 */
	private Integer revision;
	/**
	 * 创建人
	 */
	private String createdBy;
	/**
	 * 创建时间
	 */
	private Date createdTime;
	/**
	 * 更新人
	 */
	private String updatedBy;
	/**
	 * 更新时间
	 */
	private Date updatedTime;
	/**
	 * 
	 */
	private String untitled;

}
