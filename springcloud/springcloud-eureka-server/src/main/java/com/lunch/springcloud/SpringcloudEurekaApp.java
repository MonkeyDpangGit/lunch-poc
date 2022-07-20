package com.lunch.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * com.lunch.springcloud.SpringcloudEurekaApp
 *
 * @author torrisli
 * @date 2022/3/19
 * @Description: com.lunch.springcloud.SpringcloudEurekaApp
 */
@EnableEurekaServer
@SpringBootApplication
public class SpringcloudEurekaApp {

    public static void main(String[] args) {

        SpringApplication.run(SpringcloudEurekaApp.class, args);
    }
}
