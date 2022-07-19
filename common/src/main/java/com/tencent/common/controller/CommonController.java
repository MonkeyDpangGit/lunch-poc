package com.tencent.common.controller;

import com.tencent.common.service.ExecutorService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * CommonController
 *
 * @author torrisli
 * @date 2021/8/23
 * @Description: CommonController
 */
@RestController()
public class CommonController {

    @Autowired
    private ExecutorService executorService;

    @PostMapping("/capi/execute")
    public Map<String, Object> capiExecute(@RequestBody Map<String, Object> input) {

        return executorService.execute(input);
    }

    @PostMapping("/manage/execute")
    public Map<String, Object> manageExecute(@RequestBody Map<String, Object> input) {

        return executorService.execute(input);
    }

    @PostMapping("/third/execute")
    public Map<String, Object> thirdExecute(@RequestBody Map<String, Object> input) {

        return executorService.execute(input);
    }

    @PostMapping("/internal/execute")
    public Map<String, Object> internalExecute(@RequestBody Map<String, Object> input) {

        return executorService.execute(input);
    }
}
