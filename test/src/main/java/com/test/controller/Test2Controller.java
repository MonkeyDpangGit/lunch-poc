package com.test.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Test2Controller
 *
 * @author torrisli
 * @date 2021/6/11
 * @Description: Test2Controller
 */
@RestController
public class Test2Controller {

//    @Value("${test-config:}")
//    private String testConfig;
//
//    @GetMapping("/test")
//    public Map test() {
//        Map result = new HashMap();
//        result.put("test", true);
//        return result;
//    }
//
//    @GetMapping("/testConfig")
//    public String testConfig() {
//        return testConfig;
//    }

    @RequestMapping(value = "/work", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
            RequestMethod.DELETE})
    public ResponseEntity work(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Map output = new LinkedHashMap();
        output.put("success", "ok");
        output.put("query", request.getQueryString());

        HttpHeaders headers = new HttpHeaders();
        Enumeration<String> headerEnumeration = request.getHeaderNames();
        while (headerEnumeration.hasMoreElements()) {
            String headerName = headerEnumeration.nextElement();
//                String headerValue = request.getHeader(headerName);
            List<String> headerValue = Collections.list(request.getHeaders(headerName));
            headers.put(headerName, headerValue);
        }
        output.put("headers", headers);

        InputStream inputStream = null;
        try {
            inputStream = request.getInputStream();
            byte[] buffer = new byte[inputStream.available()];
            IOUtils.readFully(inputStream, buffer);
            output.put("body", buffer);

            return new ResponseEntity(output, HttpStatus.OK);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }
}
