package com.test.controller;

import com.test.pojo.Test;
import com.test.pojo.TestA;
import com.test.pojo.TestB;
import com.test.service.TestService;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * TestController
 *
 * @author torrisli
 * @date 2021/8/19
 * @Description: TestController
 */
//@RestController
//@RequestMapping("/dao")
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("/insert")
    public String insert() {

        Test test = new Test();
        test.setId(UUID.randomUUID().toString());
        test.setName("test_" + System.currentTimeMillis());
        testService.add(test);

        return "success";
    }

    @GetMapping("/insertA")
    public String insertA() {

        TestA test = new TestA();
        test.setId(UUID.randomUUID().toString());
        test.setName("test_" + System.currentTimeMillis());
        test.setA("a_" + System.currentTimeMillis());
        testService.add(test);

        return "success a";
    }

    @GetMapping("/insertB")
    public String insertB() {

        TestB test = new TestB();
        test.setId(UUID.randomUUID().toString());
        test.setName("test_" + System.currentTimeMillis());
        test.setB("b_" + System.currentTimeMillis());
        testService.add(test);

        return "success b";
    }

    @GetMapping("/find")
    public Test find(@RequestParam String name) {

        return testService.findByName(name);
    }

}
