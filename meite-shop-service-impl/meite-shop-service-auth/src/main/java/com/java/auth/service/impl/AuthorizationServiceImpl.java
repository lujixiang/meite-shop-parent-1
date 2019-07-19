package com.java.auth.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.java.auth.dao.MeiteAppInfoDao;
import com.java.auth.entity.MeiteAppInfoEntity;
import com.java.auth.service.AuthorizationService;
import com.java.auth.utils.Guid;
import com.java.core.base.Result;
import com.java.core.token.TokenUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorizationServiceImpl implements AuthorizationService {
	@Autowired
	private MeiteAppInfoDao meiteAppInfoDao;
	@Autowired
	private TokenUtil tokenUtil;

	@Override
	public Result<JSONObject> applyAppInfo(String appName) {
		// 1.验证参数
		if (StringUtils.isEmpty(appName)) {
			return Result.error("机构名称不能为空!");
		}
		// 2.生成appid和appScrec
		Guid guid = new Guid();
		String appId = guid.getAppId();
		String appScrect = guid.getAppScrect();
		// 3.添加数据库中
		MeiteAppInfoEntity meiteAppInfo = new MeiteAppInfoEntity();
		meiteAppInfo.setAppName(appName);
		meiteAppInfo.setAppId(appId);
		meiteAppInfo.setAppSecret(appScrect);
		int insertAppInfo = meiteAppInfoDao.insert(meiteAppInfo);
		if (insertAppInfo != 1) {
			return Result.error("申请失败!");
		}
		// 4.返回给客户端
		JSONObject data = new JSONObject();
		data.put("appId", appId);
		data.put("appScrect", appScrect);
		return Result.ok(data);
	}

	@Override
	public Result<JSONObject> getAccessToken(String appId, String appSecret) {
		// 使用appid+appSecret获取AccessToken
		// 1.参数验证
		if (StringUtils.isEmpty(appId)) {
			return Result.error("appId不能为空!");
		}
		if (StringUtils.isEmpty(appSecret)) {
			return Result.error("appSecret不能为空!");
		}
		// 2.使用appId+appSecret查询数据库appId, appSecret
		MeiteAppInfoEntity meiteAppInfo = meiteAppInfoDao.selectOne(new LambdaQueryWrapper<MeiteAppInfoEntity>().eq(MeiteAppInfoEntity::getAppId,appId)
												.eq(MeiteAppInfoEntity::getAppSecret,appSecret));
		if (meiteAppInfo == null) {
			return Result.error("appId或者是appSecret错误");
		}
		// 3.获取应用机构信息 生成accessToken
		String dbAppId = meiteAppInfo.getAppId();
		String accessToken = tokenUtil.createToken("auth", dbAppId);
		JSONObject data = new JSONObject();
		data.put("accessToken", accessToken);
		return Result.ok(data);
	}

	public Result<JSONObject> getAppInfo(String accessToken) {
		// 1.验证参数
		if (StringUtils.isEmpty(accessToken)) {
			return Result.error("AccessToken cannot be empty ");
		}
		// 2.从redis中获取accessToken
		String appId = tokenUtil.getToken(accessToken);
		if (StringUtils.isEmpty(appId)) {
			return Result.error("accessToken  invalid");
		}
		// 3.使用appid查询数据库
		MeiteAppInfoEntity meiteAppInfo = meiteAppInfoDao.selectOne(new LambdaQueryWrapper<MeiteAppInfoEntity>()
											.eq(MeiteAppInfoEntity::getAppId,appId));
		if (meiteAppInfo == null) {
			return Result.error("AccessToken  invalid");
		}
		// 4.返回应用机构信息
		JSONObject data = new JSONObject();
		data.put("appInfo", meiteAppInfo);
		return Result.ok(data);
	}

	// 注意：每次生成新的accessToken的时候 ,之前accessToken能够使用吗?
}
