package com.lunch.springcloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * FeignService
 *
 * @author torrisli
 * @date 2022/3/19
 * @Description: FeignService
 */
@FeignClient(value = "SPRINGCLOUD-PROVIDER")
public interface ProviderFeignService {

    @GetMapping(value = "/provider/hello")
    public String hello(@RequestParam String name);

}
