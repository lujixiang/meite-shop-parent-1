package com.java.zuul.handler.impl;

import com.google.common.util.concurrent.RateLimiter;
import com.java.core.base.Result;
import com.java.core.utils.RedisUtil;
import com.java.zuul.handler.GatewayHandler;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * 秒杀限流
 * 注:会对每个请求都限制,不只是秒杀接口
 */
@Component
@Slf4j
public class CurrentLimitHandler extends BaseHandler implements GatewayHandler {
	@Autowired
	private RedisUtil redisUtil;
	// 每秒时间存入令牌桶中Token为1个 1s/1r 写在分布式配置中心
	private RateLimiter rateLimiter = RateLimiter.create(100);


	@Override
	public void service(RequestContext ctx, HttpServletRequest req, HttpServletResponse response) {
		// 1.用户实现令牌桶限流
		log.info(">>>>>>>第一关API接口限流>>>>>");
		//todo 通过HttpServletRequest拿到请求地址,如果是app-seckill的就进行限流,否则直接走下一个handler
		boolean tryAcquire = rateLimiter.tryAcquire(0, TimeUnit.SECONDS);
		if (!tryAcquire) {
			resultError(500, ctx, "当前排队人数过多,请稍后重试....");
			return;
		}
		// 继续走下一个Handler流程判断
		nextGatewayHandler.service(ctx, req, response);

	}

}
