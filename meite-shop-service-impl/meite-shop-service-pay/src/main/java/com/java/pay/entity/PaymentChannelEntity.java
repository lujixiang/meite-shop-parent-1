package com.java.pay.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 支付渠道 
 * 
 * @author jiangli
 * @email 31346337@qq.com
 * @date 2019-07-04 11:36:14
 */
@Data
@TableName("payment_channel")
public class PaymentChannelEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@TableId
	private Integer id;
	/**
	 * 渠道名称
	 */
	private String channelName;
	/**
	 * 渠道ID
	 */
	private String channelId;
	/**
	 * 商户id
	 */
	private String merchantId;
	/**
	 * 同步回调URL
	 */
	private String syncUrl;
	/**
	 * 异步回调URL
	 */
	private String asynUrl;
	/**
	 * 公钥
	 */
	private String publicKey;
	/**
	 * 私钥
	 */
	private String privateKey;
	/**
	 * 全类名
	 */
	private String classAddress;
	/**
	 * 渠道状态 0开启1关闭
	 */
	private Integer channelState;
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

}
