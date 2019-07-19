package com.java.seckill.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 秒杀成功明细表
 * 
 * @author jiangli
 * @email 31346337@qq.com
 * @date 2019-07-17 21:38:21
 */
@Data
@TableName("meite_order")
public class MeiteOrderEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 秒杀商品id
	 */
	@TableId(type = IdType.INPUT)
	private Long seckillProductId;
	/**
	 * 用户手机号码
	 */
	private String userPhone;
	/**
	 * 状态 0:无效 1:成功 2:已付款 3:已发货
	 */
	private Integer state;
	/**
	 * 创建时间
	 */
	private Date createdTime;

}
