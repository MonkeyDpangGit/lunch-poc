package com.lunch.api.util;

import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import java.net.URI;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

public class RestTemplateUtil {

    /**
     * post请求
     *
     * @param url
     * @return
     * @throws Exception
     */
    public static JSONObject postJSON(String url) throws Exception {

        ResponseEntity<JSONObject> result = request(url, RequestMethod.POST, JSONObject.class);
        return result.getBody();
    }

    /**
     * get请求
     *
     * @param url
     * @return
     * @throws Exception
     */
    public static JSONObject getJSON(String url) throws Exception {

        ResponseEntity<JSONObject> result = request(url, RequestMethod.GET, JSONObject.class);
        return result.getBody();
    }

    /**
     * 使用RestTemplate发送请求
     *
     * @param url
     * @param method
     * @param clazz
     * @return
     * @throws Exception
     */
    public static ResponseEntity request(String url, RequestMethod method, Class clazz) throws Exception {

        // create rest tempalte
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(3 * 1000);
        requestFactory.setReadTimeout(60 * 1000);
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        ResponseErrorHandler responseErrorHandler = new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
                return true;
            }
            @Override
            public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
            }
        };
        restTemplate.setErrorHandler(responseErrorHandler);

        ResponseEntity<JSONObject> response = null;

        if (RequestMethod.GET.equals(method)) {
            // get request
            response = restTemplate.getForEntity(URI.create(url), clazz);
        } else {
            // http header
            HttpHeaders headers = new HttpHeaders();
            MediaType type = MediaType.parseMediaType("application/json;charset=UTF-8");
            headers.setContentType(type);
            headers.add("Accept", MediaType.APPLICATION_JSON.toString());

            // post request
            response = restTemplate.postForEntity(URI.create(url), new HttpEntity(headers), clazz);
        }

//        int httpCode = response.getStatusCodeValue();
//        if (httpCode != HttpStatus.OK.value()) {
//            throw new Exception("post request fail, http code " + httpCode);
//        }

        return response;
    }
}
