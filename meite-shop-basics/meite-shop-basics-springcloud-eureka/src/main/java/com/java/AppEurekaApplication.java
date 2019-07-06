package com.java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @Auther: jiangli
 * @Date: 2019/5/24 15:29
 */
@SpringBootApplication
@EnableEurekaServer
public class AppEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppEurekaApplication.class);
    }
}