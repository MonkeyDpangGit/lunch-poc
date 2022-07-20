package com.lunch.api.service.impl;

import com.lunch.api.service.DemoService;
import org.apache.dubbo.config.annotation.Service;

@Service
public class DemoServiceImpl implements DemoService {

    @Override
    public String hello(String name) {

        return "Hello, " + name;
    }
}
