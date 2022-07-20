package com.lunch.api.controller;

import com.lunch.api.service.DemoService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @Reference(check = false)
    private DemoService demoService;

    @GetMapping("/hello")
    public String hello(@RequestParam(required = false) String name) {

        return demoService.hello(name);
    }

}
