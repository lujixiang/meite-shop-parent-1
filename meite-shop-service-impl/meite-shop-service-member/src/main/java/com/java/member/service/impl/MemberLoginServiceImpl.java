package com.java.member.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.EnumUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.java.core.base.Result;
import com.java.core.constants.Constants;
import com.java.core.token.TokenUtil;
import com.java.core.utils.MD5Util;
import com.java.core.utils.RedisUtil;
import com.java.member.entity.UserDO;
import com.java.member.entity.UserTokenDO;
import com.java.member.enums.LoginTypeEnum;
import com.java.member.input.UserLoginInpDTO;
import com.java.member.mapper.UserMapper;
import com.java.member.mapper.UserTokenMapper;
import com.java.member.output.UserOutDTO;
import com.java.member.service.MemberLoginService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @Auther: jiangli
 * @Date: 2019/6/6 16:26
 */
@RestController
public class MemberLoginServiceImpl implements MemberLoginService {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private TokenUtil tokenUtil;
	@Autowired
	private UserTokenMapper userTokenMapper;
	@Autowired
	private RedisUtil redisUtil;

	@Override
	@Transactional
	public Result<JSONObject> login(@RequestBody UserLoginInpDTO userLoginInpDTO) {
		// todo 验证参数
		//验证登录类型,目的限制登录范围
		String loginType = userLoginInpDTO.getLoginType();
		List<String> loginTypes = EnumUtil.getNames(LoginTypeEnum.class);
		if (!loginTypes.contains(loginType)) {
			return Result.error("登陆类型错误!");
		}
		// 根据手机号码查询数据库
		UserDO userDO = userMapper.selectOne(new LambdaQueryWrapper<UserDO>().eq(UserDO::getMobile, userLoginInpDTO.getMobile()));
		if (userDO == null || !Objects.equals(MD5Util.MD5(userLoginInpDTO.getPassword()), userDO.getPassword())) {
			return Result.error("用户名或者密码不正确!");
		}
		//获取用户id
		Integer userId = userDO.getUserId();
		//根据userId+loginType 查询当前登陆类型账号之前是否有登陆过，如果登陆过 清除之前redis token
		UserTokenDO userTokenDO = userTokenMapper.selectOne(new LambdaQueryWrapper<UserTokenDO>().eq(UserTokenDO::getUserId, userId).eq(UserTokenDO::getLoginType, loginType).eq(UserTokenDO::getIsAvailability,0));
		if (userTokenDO != null) {
			String token = userTokenDO.getToken();
			tokenUtil.removeToken(token);
			//将数据库中对应的记录置为不可用
			userTokenDO.setIsAvailability(1);
			userTokenMapper.updateById(userTokenDO);
		}
		//如果有openid,则保存到数据库
		if (StringUtils.isNotEmpty(userLoginInpDTO.getQqOpenId())) {
			userDO.setQqOpenid(userLoginInpDTO.getQqOpenId());
			userMapper.updateById(userDO);
		}

		//用户登录token session的区别
		//用户每一个端登录成功之后,会对应生成一个token令牌(临时且唯一),存放在redis中作为redis key,value为userId
		String token = tokenUtil.createToken(Constants.MEMBER_TOKEN_KEYPREFIX + loginType, String.valueOf(userDO.getUserId()));
		UserTokenDO userToken = new UserTokenDO();
		userToken.setToken(token);
		userToken.setLoginType(loginType);
		userToken.setDeviceInfo(userLoginInpDTO.getDeviceInfo());
		userToken.setUserId(userId);
		userTokenMapper.insert(userToken);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("token", token);
		return Result.ok(jsonObject);
	}

	@Override
	public Result<UserOutDTO> getInfo(String token) {
		// 1.参数验证
		if (StringUtils.isEmpty(token)) {
			return Result.error("token不能为空!");
		}
		// 2.使用token向redis中查询userId
		String redisValue = tokenUtil.getToken(token);
		if (StringUtils.isEmpty(redisValue)) {
			return Result.error("token已经失效或者不正确");
		}
		Integer userId = Convert.toInt(redisValue);
		// 3.根据userId查询用户信息
		UserDO userDo = userMapper.selectOne(new LambdaQueryWrapper<UserDO>().eq(UserDO::getUserId, userId).eq(UserDO::getIsAvalible, 0));
		if (userDo == null) {
			return Result.error("用户信息不存在!");
		}
		// 4.将Do转换为Dto
		UserOutDTO userOutDTO = Convert.convert(UserOutDTO.class, userDo);
		return Result.ok(userOutDTO);
	}

	@Override
	public Result<JSONObject> delToken(String token) {
		UserTokenDO userTokenDO = userTokenMapper.selectOne(new LambdaQueryWrapper<UserTokenDO>().eq(UserTokenDO::getToken, token));
		userTokenDO.setIsAvailability(1);
		userTokenMapper.updateById(userTokenDO);
		Boolean delKey = redisUtil.delKey(token);
		return delKey ? Result.ok("删除成功") : Result.error("删除失败!");
	}

	@Override
	public Result<UserOutDTO> ssoLogin(@RequestBody UserLoginInpDTO userLoginInpDTO) {
		//验证登录类型,目的限制登录范围
		String loginType = userLoginInpDTO.getLoginType();
		List<String> loginTypes = EnumUtil.getNames(LoginTypeEnum.class);
		if (!loginTypes.contains(loginType)) {
			return Result.error("登陆类型错误!");
		}
		// 根据手机号码查询数据库
		UserDO userDO = userMapper.selectOne(new LambdaQueryWrapper<UserDO>().eq(UserDO::getMobile, userLoginInpDTO.getMobile()));
		if (userDO == null || !Objects.equals(MD5Util.MD5(userLoginInpDTO.getPassword()), userDO.getPassword())) {
			return Result.error("用户名或者密码不正确!");
		}
		return Result.ok(Convert.convert(UserOutDTO.class,userDO));
	}

}
