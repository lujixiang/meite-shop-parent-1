package com.java.zuul.entity;

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
 * @date 2019-07-09 21:15:24
 */
@Data
@TableName("meite_black_list")
public class MeiteBlackListEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 
	 */
	private String ipAddress;
	/**
	 * 限制类型 1:黑名单
	 */
	private Integer restrictionType;
	/**
	 * 是否可用
	 */
	private Integer availability;
	/**
	 * 乐观锁
	 */
	private Integer revision;
	/**
	 * 
	 */
	private String createdBy;
	/**
	 * 
	 */
	private Date createdTime;
	/**
	 * 
	 */
	private String updatedBy;
	/**
	 * 
	 */
	private Date updatedTime;

}
