package com.test.service;

import com.test.dao.TestRepository;
import com.test.pojo.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * TestService
 *
 * @author torrisli
 * @date 2021/8/19
 * @Description: TestService
 */
@Component
public class TestService {

    @Autowired
    private TestRepository testRepository;

    public Test findByName(String name) {
        return testRepository.findByName(name);
    }

    public void add(Test test) {
        testRepository.insert(test);
    }
}
