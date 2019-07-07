package com.java.pay.callback.template.factory;

import com.java.core.utils.SpringContextUtil;
import com.java.pay.callback.template.AbstractPayCallbackTemplate;

/**
 * 获取具体实现的模版工厂方案
 */
public class TemplateFactory {

	public static AbstractPayCallbackTemplate getPayCallbackTemplate(String beanId) {
		return (AbstractPayCallbackTemplate) SpringContextUtil.getBean(beanId);
	}

}
