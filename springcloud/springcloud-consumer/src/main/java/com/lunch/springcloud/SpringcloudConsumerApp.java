package com.lunch.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * SpringcloudProviderApp
 *
 * @author torrisli
 * @date 2022/3/19
 * @Description: SpringcloudProviderApp
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class SpringcloudConsumerApp {

    public static void main(String[] args) {

        SpringApplication.run(SpringcloudConsumerApp.class, args);
    }
}
