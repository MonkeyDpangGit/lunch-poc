package com.tencent.common.service;

import com.tencent.common.dao.DemoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * DemoService
 *
 * @author torrisli
 * @date 2021/8/24
 * @Description: DemoService
 */
@Component
public class DemoService {

    @Autowired
    private DemoRepository demoRepository;

    public DemoRepository getDemoRepository() {
        return demoRepository;
    }
}
