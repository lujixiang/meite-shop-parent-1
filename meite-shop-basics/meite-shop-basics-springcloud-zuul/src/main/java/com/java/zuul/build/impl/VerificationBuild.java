package com.java.zuul.build.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.java.core.sign.SignUtil;
import com.java.zuul.build.GatewayBuild;
import com.java.zuul.dao.MeiteBlackListDao;
import com.java.zuul.entity.MeiteBlackListEntity;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 参数验证
 * 
 */
@Slf4j
@Component
public class VerificationBuild implements GatewayBuild {
	@Autowired
	private MeiteBlackListDao meiteBlackListDao;

	@Override
	public Boolean blackBlock(RequestContext ctx, String ipAddres, HttpServletResponse response) {
		// 2.查询数据库黑名单
		MeiteBlackListEntity meiteBlacklist = meiteBlackListDao.selectOne(new LambdaQueryWrapper<MeiteBlackListEntity>().eq(MeiteBlackListEntity::getIpAddress,ipAddres)
				.eq(MeiteBlackListEntity::getRestrictionType,1));
		if (meiteBlacklist != null) {
			resultError(ctx, "ip:" + ipAddres + ",Insufficient access rights");
			return false;
		}
		log.info(">>>>>>ip:{},验证通过>>>>>>>", ipAddres);
		// 3.将ip地址传递到转发服务中
		response.addHeader("ipAddres", ipAddres);
		log.info(">>>>>>ip:{},验证通过>>>>>>>", ipAddres);
		return true;
	}

	@Override
	public Boolean toVerifyMap(RequestContext ctx, String ipAddres, HttpServletRequest request) {
		// 4.外网传递参数验证
		Map<String, String> verifyMap = SignUtil.toVerifyMap(request.getParameterMap(), false);
		if (!SignUtil.verify(verifyMap)) {
			resultError(ctx, "ip:" + ipAddres + ",Sign fail");
			return false;
		}
		return true;
	}

	private void resultError(RequestContext ctx, String errorMsg) {
		ctx.setResponseStatusCode(401);
		ctx.setSendZuulResponse(false);
		ctx.setResponseBody(errorMsg);

	}
}
