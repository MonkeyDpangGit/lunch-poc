package com.lunch.springcloud.configuration;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * LoadBalanceConf
 *
 * @author torrisli
 * @date 2022/3/19
 * @Description: LoadBalanceConf
 */
@Configuration
public class LoadBalanceConf {

    @Bean
//    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public IRule ribbonRule() {
        // 这里配置策略，和配置文件对应
        return new RandomRule();
    }
}
