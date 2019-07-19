package com.java.zuul.build;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netflix.zuul.context.RequestContext;

/**
 * 连接build
 */
@Component
public class GatewayDirector {
	@Resource(name = "verificationBuild") //使用的实现类
	private GatewayBuild gatewayBuild;

	public void direcot(RequestContext ctx, String ipAddres, HttpServletResponse response, HttpServletRequest request) {
		// 1.黑名单
		Boolean blackBlock = gatewayBuild.blackBlock(ctx, ipAddres, response);
		if (!blackBlock) {
			return;
		}
//		// // 2.参数验证
//		Boolean verifyMap = gatewayBuild.toVerifyMap(ctx, ipAddres, request);
//		if (!verifyMap) {
//			return;
//		}
		// 3.验证accessToken
		Boolean apiAuthority = gatewayBuild.apiAuthority(ctx, request);
		if (!apiAuthority) {
			return;
		}
	}

}
