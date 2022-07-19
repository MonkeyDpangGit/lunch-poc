package com.lunch.api.util;

import com.alibaba.fastjson.JSONObject;
import java.net.URLEncoder;
import java.util.Map;
import java.util.TreeMap;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;

/**
 * @author torrisli
 * @date 2021/1/4
 * @Description: TODO
 */
public class UrlUtil {

//    private static final String MAIN_PAGE = "https://app1.demo.tencentidentity.com/main.html";
private static final String MAIN_PAGE = "/main.html";

    public static Map<String, Object> getParameterMap(HttpServletRequest request) {

        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> resultMap = new TreeMap<String, Object>();
        parameterMap.forEach((k, v) -> {
            String s = StringUtils.join(v);
            resultMap.put(k, s);
        });
        return resultMap;
    }

    public static String getRedirectUrl(Map<String, Object> parameterMap, String message) throws Exception {

        String queryStringJson = JSONObject.toJSONString(parameterMap);

        StringBuilder url = new StringBuilder(MAIN_PAGE);
        url.append("?param=");
        url.append(URLEncoder.encode(queryStringJson, "UTF-8"));
        if (StringUtils.isNotBlank(message)) {
            url.append("&message=");
            url.append(URLEncoder.encode(message, "UTF-8"));
        }
        return url.toString();
    }

    public static String getRedirectUrl(HttpServletRequest request, String message) throws Exception {

        Map<String, Object> parameterMap = getParameterMap(request);
        return getRedirectUrl(parameterMap, message);
    }

    public static String getRedirectUrl(HttpServletRequest request) throws Exception {

        return getRedirectUrl(request, null);
    }

    public static void main(String[] args) {

        Object a = null;
        System.out.println((String) a);
    }
}
