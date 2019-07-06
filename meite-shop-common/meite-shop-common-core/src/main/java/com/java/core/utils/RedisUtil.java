package com.java.core.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Auther: jiangli
 * @Date: 2019/6/4 09:48
 */
@Component
public class RedisUtil {
	@Autowired
	private RedisTemplate redisTemplate;

	/**
	 * 存放string类型
	 * 
	 * @param key
	 *            key
	 * @param data
	 *            数据
	 * @param timeout
	 *            超时间(秒)
	 */
	public void setString(String key, String data, Long timeout) {
		redisTemplate.opsForValue().set(key, data);
		if (timeout != null) {
			redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
		}
	}

	/**
	 * 存放string类型
	 * 
	 * @param key
	 *            key
	 * @param data
	 *            数据
	 */
	public void setString(String key, String data) {
		setString(key, data, null);
	}

	/**
	 * 根据key查询string类型
	 * 
	 * @param key
	 * @return
	 */
	public String getString(String key) {
		String value = (String) redisTemplate.opsForValue().get(key);
		return value;
	}

	/**
	 * 根据对应的key删除key
	 *
	 * @param key
	 */
	public boolean delKey(String key) {
		return redisTemplate.delete(key);
	}
}
