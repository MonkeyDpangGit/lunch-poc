package com.lunch.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * SpringcloudProviderApp
 *
 * @author torrisli
 * @date 2022/3/19
 * @Description: SpringcloudProviderApp
 */
@EnableDiscoveryClient
@SpringBootApplication
public class SpringcloudProviderApp {

    public static void main(String[] args) {

        SpringApplication.run(SpringcloudProviderApp.class, args);
    }
}
