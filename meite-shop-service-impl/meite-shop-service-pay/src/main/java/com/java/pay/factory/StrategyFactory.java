package com.java.pay.factory;

import com.java.pay.strategy.PayStrategy;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jiangli
 * @date 2019/7/6 18:45
 * 初始化不同的策略行为
 */
public class StrategyFactory {

	private static Map<String, PayStrategy> payStrategyMap = new ConcurrentHashMap<>();

	public static PayStrategy getPayStrategy(String classAddress) {
		if (StringUtils.isEmpty(classAddress)) {
			return null;
		}

		try {
			PayStrategy payStrategyBean = payStrategyMap.get(classAddress);
			if (payStrategyBean != null) {
				return payStrategyBean;
			}

			Class<?> forName = Class.forName(classAddress);
			PayStrategy payStrategy = (PayStrategy) forName.newInstance();
			payStrategyMap.put(classAddress, payStrategy);
			return payStrategy;
		} catch (Exception e) {
			return null;
		}
	}
}
