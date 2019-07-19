package com.java.auth.entity;

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
 * @date 2019-07-11 22:36:30
 */
@Data
@TableName("meite_app_info")
public class MeiteAppInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;
	/**
	 * 应用名称
	 */
	private String appName;
	/**
	 * 应用id
	 */
	private String appId;
	/**
	 * 应用密钥
	 */
	private String appSecret;
	/**
	 * 是否可用
	 */
	private Integer availability;

}
