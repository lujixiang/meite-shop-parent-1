package com.java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author jiangli
 * @date 2019/7/18 21:23
 */
@SpringBootApplication
@EnableEurekaClient
public class SeckillConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeckillConsumerApplication.class);
	}
}
