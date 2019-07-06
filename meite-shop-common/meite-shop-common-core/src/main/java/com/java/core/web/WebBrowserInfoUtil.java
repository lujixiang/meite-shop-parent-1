package com.java.core.web;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: jiangli
 * @Date: 2019/6/21 10:40
 */
public class WebBrowserInfoUtil {

	/**
	 * 获取浏览器信息
	 *
	 * @return
	 */
	public static String webBrowserInfo(HttpServletRequest request) {
		UserAgent ua = UserAgentUtil.parse(request.getHeader("User-Agent"));
		// 操作系统+浏览器信息+浏览器版本号
		return ua.getOs().toString() + "/" + ua.getBrowser().toString() + "/" + ua.getVersion();
	}
}