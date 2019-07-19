package com.java.zuul.filter;

import com.java.zuul.build.GatewayDirector;
import com.java.zuul.handler.GatewayHandler;
import com.java.zuul.handler.ResponsibilityClient;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Component
@Slf4j
public class GatewayFilter extends ZuulFilter {
	@Autowired
	private GatewayDirector gatewayDirector;
	@Autowired
	private ResponsibilityClient responsibilityClient;

	@Override
	public Object run() throws ZuulException {
		RequestContext ctx = RequestContext.getCurrentContext();
		// 1.获取请求对象
		HttpServletRequest request = ctx.getRequest();
		HttpServletResponse response = ctx.getResponse();
		GatewayHandler handler = responsibilityClient.getHandler();
		handler.service(ctx, request, response);
		return null;
	}

	/**
	 * 过滤参数
	 */
	private Map<String, List<String>> filterParameters(HttpServletRequest request, RequestContext ctx) {
		Map<String, List<String>> requestQueryParams = ctx.getRequestQueryParams();
		if (requestQueryParams == null) {
			requestQueryParams = new HashMap<>();
		}
		Enumeration em = request.getParameterNames();
		while (em.hasMoreElements()) {
			String name = (String) em.nextElement();
			String value = request.getParameter(name);
			ArrayList<String> arrayList = new ArrayList<>();
			// 将参数转化为html参数 防止xss攻击
			arrayList.add(StringEscapeUtils.escapeHtml(value));
			requestQueryParams.put(name, arrayList);
		}
		return requestQueryParams;
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