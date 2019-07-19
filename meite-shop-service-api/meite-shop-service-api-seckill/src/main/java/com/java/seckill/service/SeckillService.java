package com.java.seckill.service;

import com.alibaba.fastjson.JSONObject;
import com.java.core.base.Result;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author jiangli
 * @date 2019/7/17 21:11
 */
public interface SeckillService {

	/**
	 * 商品秒杀接口
	 */
	@GetMapping("/seckill")
	Result<JSONObject> seckill(String phone,Long seckillProductId);

	/**
	 * 生成秒杀商品库存令牌桶
	 */
	@GetMapping("/addSeckillToken")
	Result<JSONObject> addSeckillToken(Long seckillProductId, Long tokenCount);
}
