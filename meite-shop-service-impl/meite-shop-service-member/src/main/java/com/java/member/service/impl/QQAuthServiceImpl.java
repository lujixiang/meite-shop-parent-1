package com.java.member.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.java.core.base.Result;
import com.java.core.constants.Constants;
import com.java.core.constants.StatusCode;
import com.java.core.token.TokenUtil;
import com.java.member.entity.UserDO;
import com.java.member.mapper.UserMapper;
import com.java.member.service.QQAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: jiangli
 * @Date: 2019/6/17 14:49
 */
@RestController
public class QQAuthServiceImpl implements QQAuthService {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private TokenUtil tokenUtil;

	@Override
	public Result<JSONObject> findByOpenId(String openId) {
		UserDO userDO = userMapper.selectOne(new LambdaQueryWrapper<UserDO>().eq(UserDO::getQqOpenid, openId));
		//如果没有查到
		if (userDO == null) {
			return Result.error(StatusCode.NORESULT,"根据openId没有查询到用户信息");
		}
		//返回token
		String token = tokenUtil.createToken(Constants.MEMBER_TOKEN_KEYPREFIX + "QQ_LOGIN", String.valueOf(userDO.getUserId()));
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("token", token);
		return Result.ok(jsonObject);
	}
}