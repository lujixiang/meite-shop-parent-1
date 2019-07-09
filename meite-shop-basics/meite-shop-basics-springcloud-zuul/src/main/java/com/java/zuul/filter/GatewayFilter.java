package com.java.zuul.filter;

import com.java.zuul.build.GatewayDirector;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class GatewayFilter extends ZuulFilter {
	@Autowired
	private GatewayDirector gatewayDirector;

	@Override
	public Object run() throws ZuulException {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		HttpServletResponse response = ctx.getResponse();
		response.setContentType("UTF-8");
		// 1.获取ip地址
		String ipAddres = getIpAddr(request);
		if (StringUtils.isEmpty(ipAddres)) {
			resultError(ctx, "未能够获取到ip地址");
		}
		// 2. 验证黑名单 验证签名
		gatewayDirector.direcot(ctx,ipAddres,response,request);
		return null;
	}

	private void resultError(RequestContext ctx, String errorMsg) {
		ctx.setResponseStatusCode(401);
		ctx.setSendZuulResponse(false);
		ctx.setResponseBody(errorMsg);

	}

	@Override
	public boolean shouldFilter() {

		return true;
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	/**
	 * 在方法之前拦截
	 * 
	 * @return
	 */
	@Override
	public String filterType() {

		return "pre";
	}

	/**
	 * 获取Ip地址
	 * 
	 * @param request
	 * @return
	 */
	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

}