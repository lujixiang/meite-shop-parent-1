package com.java.zuul.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author jiangli
 * @email 31346337@qq.com
 * @date 2019-07-19 21:31:24
 */
@Data
@TableName("gateway_handler")
public class GatewayHandlerEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;
	/**
	 * 
	 */
	private String handlerName;
	/**
	 * 
	 */
	private String handlerId;
	/**
	 * 
	 */
	private String prevHandlerId;
	/**
	 * 
	 */
	private String nextHandlerId;
	/**
	 * 
	 */
	private Integer isOpen;

}
