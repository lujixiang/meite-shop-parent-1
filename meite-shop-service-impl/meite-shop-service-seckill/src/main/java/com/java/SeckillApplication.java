package com.java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author jiangli
 * @date 2019/7/17 21:51
 */
@SpringBootApplication
@EnableEurekaClient
@EnableAsync
@EnableHystrix
public class SeckillApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeckillApplication.class);
	}
}
