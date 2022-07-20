package com.test.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TestController
 *
 * @author torrisli
 * @date 2021/6/11
 * @Description: TestController
 */
@RestController
public class TestController {

    @Value("${test-config:}")
    private String testConfig;

    @GetMapping("/test")
    public Map test() {
        Map result = new HashMap();
        result.put("test", true);
        return result;
    }

    @GetMapping("/testConfig")
    public String testConfig() {
        return testConfig;
    }
}
