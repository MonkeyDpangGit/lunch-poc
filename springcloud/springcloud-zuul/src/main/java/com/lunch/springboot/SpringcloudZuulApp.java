package com.lunch.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * SpringcloudZuulApp
 *
 * @author torrisli
 * @date 2022/3/19
 * @Description: SpringcloudZuulApp
 */
@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
public class SpringcloudZuulApp {

    public static void main(String[] args) {

        SpringApplication.run(SpringcloudZuulApp.class, args);
    }
}
