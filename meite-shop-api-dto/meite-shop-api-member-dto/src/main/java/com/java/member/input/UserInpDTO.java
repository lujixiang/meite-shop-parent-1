package com.java.member.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Auther: jiangli
 * @Date: 2019/6/5 17:02
 */
@Data
@ApiModel(value = "用户接收实体类")
public class UserInpDTO {
	/**
	 * userid
	 */
	@ApiModelProperty(value = "用户id")
	private Integer userId;
	/**
	 * 手机号码
	 */
	@ApiModelProperty(value = "手机号码")
	private String mobile;
	/**
	 * 邮箱
	 */
	@ApiModelProperty(value = "邮箱")
	private String email;
	/**
	 * 密码
	 */
	@ApiModelProperty(value = "密码")
	private String password;
	/**
	 * 用户名称
	 */
	@ApiModelProperty(value = "用户名称")
	private String username;
	/**
	 * 性别 0 男 1女
	 */
	@ApiModelProperty(value = "用户性别")
	private Integer sex;
	/**
	 * 年龄
	 */
	@ApiModelProperty(value = "用户年龄")
	private Integer age;
	/**
	 * 用户头像
	 */
	@ApiModelProperty(value = " 用户头像")
	private String picImg;
	/**
	 * 用户关联 QQ 开放ID
	 */
	@ApiModelProperty(value = "用户关联 QQ 开放ID")
	private Date qqOpenid;
	/**
	 * 用户关联 微信 开放ID
	 */
	@ApiModelProperty(value = "用户关联 微信 开放ID")
	private Date wxOpenid;
	/**
	 * 注册验证码
	 */
	@ApiModelProperty(value = "注册验证码")
	private String registerCode;
}