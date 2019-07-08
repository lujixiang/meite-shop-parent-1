package com.java.pay.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.java.pay.mq.producer.PointMessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jiangli
 * @date 2019/7/8 20:49
 */
@RestController
public class TestMQServiceImpl {

	@Autowired
	private PointMessageProducer pointMessageProducer;

	@GetMapping("/send")
	public String send() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("paymentId",System.currentTimeMillis());
		jsonObject.put("userId","31346337");
		jsonObject.put("integral",10);
		pointMessageProducer.send(jsonObject);
		return "success";
	}
}
