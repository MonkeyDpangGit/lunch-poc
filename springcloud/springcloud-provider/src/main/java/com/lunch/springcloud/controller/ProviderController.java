package com.lunch.springcloud.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * ProviderController
 *
 * @author torrisli
 * @date 2022/3/19
 * @Description: ProviderController
 */
@RestController
public class ProviderController {

    @GetMapping("/provider/hello")
    public String hello(@RequestParam(required = false) String name) {

        return "hello, " + name;
    }
}
