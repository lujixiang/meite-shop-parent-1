package com.java.member.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户登录token表
 * 
 * @author jiangli
 * @email 31346337@qq.com
 * @date 2019-06-10 17:48:05
 */
@Data
@TableName("meite_user_token")
public class UserTokenDO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * token令牌
	 */
	private String token;
	/**
	 * 登录类型
	 */
	private String loginType;
	/**
	 * 登录设备
	 */
	private String deviceInfo;
	/**
	 * 是否可用 0:可用 1:失效
	 */
	private Integer isAvailability;
	/**
	 * 用户id
	 */
	private Integer userId;
	/**
	 * 登录时间
	 */
	private Date loginTime;

}
