package com.lunch.springcloud.controller;

import com.lunch.springcloud.service.ProviderFeignService;
import com.netflix.discovery.converters.Auto;
import javax.annotation.Resource;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * ConsumerController
 *
 * @author torrisli
 * @date 2022/3/19
 * @Description: ConsumerController
 */
@RestController
public class ConsumerController {

    @Autowired
    private RestTemplate restTemplate;

    @Resource
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private ProviderFeignService providerFeignService;

    @GetMapping("/consumer/ribbon/hello")
    public String ribbonHello(@RequestParam(required = false) String name) {

//        return restTemplate.getForObject("http://SPRINGCLOUD-PROVIDER/provider/hello?name=" + name, String.class);

        ServiceInstance serviceInstance = loadBalancerClient.choose("SPRINGCLOUD-PROVIDER");
        return restTemplate.getForObject(
                "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/provider/hello?name="
                        + name, String.class);

    }
//
    @GetMapping("/consumer/feign/hello")
    public String feignHello(@RequestParam(required = false) String name) {

        return providerFeignService.hello(name);
    }
}
