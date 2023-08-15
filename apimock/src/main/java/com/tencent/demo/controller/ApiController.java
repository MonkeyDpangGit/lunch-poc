package com.tencent.demo.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import okhttp3.Headers;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * ApiController
 *
 * @author torrisli
 * @date 2022/6/7
 * @Description: ApiController
 */
@RestController
public class ApiController {

    private AtomicLong count = new AtomicLong(0);

    private Long beginTime = System.currentTimeMillis();

    private RestTemplate restTemplate = new RestTemplate();

    @ResponseBody
    @RequestMapping(value = "/tax/getLimit", method = {RequestMethod.GET, RequestMethod.POST})
    public Map getLimit() {

        Map output = new HashMap();
        output.put("taxpayerLimit", 100);
        output.put("apiLimit", 100);
        return output;
    }

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
            output.put("body", IOUtils.readLines(inputStream, "UTF-8"));

            return new ResponseEntity(output, HttpStatus.OK);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }

    @RequestMapping(value = "/apigateway", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
            RequestMethod.DELETE})
    public ResponseEntity apigateway(HttpServletRequest request, HttpServletResponse response) {

        long beginTime = System.currentTimeMillis();

        InputStream payloadInputStream = null;
        try {
            // method
            String method = request.getMethod();
            // query
            String queryString = request.getQueryString();
            // header
//            Map<String, String> headers = new LinkedHashMap();
            HttpHeaders headers = new HttpHeaders();
            Enumeration<String> headerEnumeration = request.getHeaderNames();
            while (headerEnumeration.hasMoreElements()) {
                String headerName = headerEnumeration.nextElement();
//                String headerValue = request.getHeader(headerName);
                List<String> headerValue = Collections.list(request.getHeaders(headerName));
                headers.put(headerName, headerValue);
            }
            // payload
            payloadInputStream = request.getInputStream();
            byte[] readResult = new byte[payloadInputStream.available()];
            IOUtils.readFully(payloadInputStream, readResult);

            long parseTime = System.currentTimeMillis();

            // send
            String backendUrl = "http://localhost:8888/work";
            if (StringUtils.isNotBlank(queryString)) {
                backendUrl = backendUrl + "?" + queryString;
            }

            HttpEntity requestEntity = new HttpEntity(readResult, headers);

            ResponseEntity<Object> responseEntity = restTemplate
                    .exchange(backendUrl, HttpMethod.valueOf(method), requestEntity, Object.class);

            long sendTime = System.currentTimeMillis();

            System.out
                    .println(System.currentTimeMillis() + " [parse] " + (parseTime - beginTime) + " [send] " + (sendTime
                            - parseTime));

            return responseEntity;

        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.ok(null);
        } finally {
            count.incrementAndGet();
            IOUtils.closeQuietly(payloadInputStream);
        }
    }

    @RequestMapping(value = "/start", method = {RequestMethod.GET})
    public Map start() {

        beginTime = System.currentTimeMillis();
        count = new AtomicLong(0);

        Map result = new HashMap();
        result.put("beginTime", beginTime);
        return result;
    }

    @RequestMapping(value = "/end", method = {RequestMethod.GET})
    public Map end() {

        Long endTime = System.currentTimeMillis();

        Map result = new HashMap();
        result.put("beginTime", beginTime);
        result.put("endTime", endTime);

        long seconds = (endTime - beginTime) / 1000;
        result.put("runTime", seconds);

        long totalCount = count.get();
        result.put("count", count.get());
        result.put("tps", (totalCount / seconds));
        return result;
    }

    private Headers setHeaderParams(Map<String, String> headerParams) {

        Headers headers = null;
        Headers.Builder headersbuilder = new Headers.Builder();
        if (headerParams != null && headerParams.size() > 0) {
            for (String key : headerParams.keySet()) {
                if (!StringUtils.isEmpty(key) && !StringUtils.isEmpty(headerParams.get(key))) {
                    //如果参数不是null并且不是""，就拼接起来
                    headersbuilder.add(key, headerParams.get(key));
                }
            }
        }
        headers = headersbuilder.build();
        return headers;
    }
}
