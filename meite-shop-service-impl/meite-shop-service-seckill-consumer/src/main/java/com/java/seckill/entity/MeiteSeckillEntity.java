package com.java.seckill.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 秒杀库存表
 * 
 * @author jiangli
 * @email 31346337@qq.com
 * @date 2019-07-17 21:38:21
 */
@Data
@TableName("meite_seckill")
public class MeiteSeckillEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 秒杀商品id
	 */
	@TableId(type = IdType.INPUT)
	private Long seckillProductId;
	/**
	 * 商品名称
	 */
	private String productName;
	/**
	 * 库存
	 */
	private Integer stock;
	/**
	 * 开始时间
	 */
	private Date startTime;
	/**
	 * 结束时间
	 */
	private Date endTime;
	/**
	 * 创建时间
	 */
	private Date createdTime;
	/**
	 * 版本号
	 */
	private Long version;

}
