package com.java.seckill.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.java.core.base.Result;
import com.java.core.token.TokenUtil;
import com.java.core.utils.RedisUtil;
import com.java.seckill.dao.MeiteOrderDao;
import com.java.seckill.dao.MeiteSeckillDao;
import com.java.seckill.entity.MeiteOrderEntity;
import com.java.seckill.entity.MeiteSeckillEntity;
import com.java.seckill.producer.SeckillProducer;
import com.java.seckill.service.SeckillService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jiangli
 * @date 2019/7/17 21:17
 */
@RestController
@Slf4j
public class SeckillServiceImpl implements SeckillService {
	@Autowired
	private MeiteSeckillDao meiteSeckillDao;
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private TokenUtil tokenUtil;
	@Autowired
	private SeckillProducer seckillProducer;

	private static final String SECKILL_REDISE_KEY = "seckill_";

	@Override
	@Transactional
	@HystrixCommand(fallbackMethod = "spikeFallback")
	public Result<JSONObject> seckill(String phone, Long seckillProductId) {
		//1.参数校验
		MeiteSeckillEntity meiteSeckillEntity = meiteSeckillDao.selectById(seckillProductId);
		if (meiteSeckillEntity == null) {
			return Result.error("秒杀商品不存在!");
		}
		// 1.秒杀服务实现限流

		// boolean tryAcquire = rateLimiter.tryAcquire(0, TimeUnit.SECONDS);
		// if (!tryAcquire) {
		// return setResultError("服务忙，请稍后重试!");
		// }

		//2.用户频率限制
		Boolean nx = redisUtil.setNx(SECKILL_REDISE_KEY + phone, String.valueOf(seckillProductId), 10L);
		if (!nx) {
			return Result.error("访问过于频繁,请稍后重试!");
		}

		//2.从redis list中获取秒杀token,以出栈的方式弹出1个
		String token = tokenUtil.getListKeyToken(String.valueOf(seckillProductId));
		if (token == null) {
			log.info("seckillProductId:{},秒杀商品已经售罄", seckillProductId);
			return Result.ok("秒杀商品已经售罄,请下次再来!");
		}
		//3.获取到秒杀token之后,使用mq异步修改商品库存
		sendSeckillMsg(phone, seckillProductId);

		return Result.ok("正在排队中......");
	}

	private Result<JSONObject> spikeFallback(String phone, Long seckillId) {
		return Result.error("服务器忙,请稍后重试!");
	}

	/**
	 * 获取到秒杀token之后,使用mq异步修改商品库存
	 *
	 * @param phone
	 * @param seckillProductId
	 */
	@Async
	private void sendSeckillMsg(String phone, Long seckillProductId) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("phone", phone);
		jsonObject.put("seckillProductId", seckillProductId);
		seckillProducer.send(jsonObject);
	}

	//redis数据类型为list,key为商品id,list为多个秒杀token
	@Override
	public Result<JSONObject> addSeckillToken(Long seckillProductId, Long tokenCount) {
		//1.参数校验
		MeiteSeckillEntity meiteSeckillEntity = meiteSeckillDao.selectById(seckillProductId);
		if (meiteSeckillEntity == null) {
			return Result.error("秒杀商品不存在!");
		}
		//2.使用多线程异步生成令牌
		createSeckillToken(seckillProductId, tokenCount);
		return Result.ok("秒杀商品库存令牌正在生成中......");
	}

	@Async
	private void createSeckillToken(Long seckillProductId, Long tokenCount) {
		tokenUtil.createListToken(String.valueOf(seckillProductId), "seckill_", tokenCount);
	}
}
