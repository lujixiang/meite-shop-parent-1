package com.java.member.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Auther: jiangli
 * @Date: 2019/6/6 16:17
 */
@Data
@ApiModel(value = "用户登录信息参数")
public class UserLoginInpDTO {

	/**
	 * 手机号码
	 */
	@ApiModelProperty(value = "手机号码")
	private String mobile;
	/**
	 * 密码
	 */
	@ApiModelProperty(value = "密码")
	private String password;

	/**
	 * 登陆类型 PC、Android 、IOS
	 */
	@ApiModelProperty(value = "登陆类型")
	private String loginType;
	/**
	 * 设备信息
	 */
	@ApiModelProperty(value = "设备信息")
	private String deviceInfo;
	/**
	 * 腾讯开放ID
	 */
	@ApiModelProperty(value = "腾讯开放ID")
	private String qqOpenId;

}