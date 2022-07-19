package com.tencent.demo.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * ApiController
 *
 * @author torrisli
 * @date 2022/6/7
 * @Description: ApiController
 */
@RestController
public class ApiController {

    @ResponseBody
    @RequestMapping(value = "/tax/getLimit", method = {RequestMethod.GET, RequestMethod.POST})
    public Map getLimit() {

        Map output = new HashMap();
        output.put("taxpayerLimit", 100);
        output.put("apiLimit", 100);
        return output;
    }
}
