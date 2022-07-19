package com.tencent.common.utils;

import com.alibaba.fastjson.JSON;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The type Request utils.
 */
public class RequestUtils {

    private static final String[] IP_HEADER_CANDIDATES = {"X-real-ip", "X-Forwarded-For", "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR", "HTTP_X_FORWARDED", "HTTP_X_CLUSTER_CLIENT_IP", "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR", "HTTP_FORWARDED", "HTTP_VIA", "REMOTE_ADDR"};
    private static Logger logger = LoggerFactory.getLogger(RequestUtils.class);

    /**
     * 获取客户端操作系统
     *
     * @param request the request
     * @return the string
     */
    public static String parseClientOS(HttpServletRequest request) {
        OperatingSystem os = UserAgent.parseUserAgentString(browserAgent(request)).getOperatingSystem();
        return os.getName().toLowerCase().replace(" ", "");
    }

    /**
     * 获取UserAgent
     *
     * @param request the request
     * @return the string
     */
    public static String parseUserAgent(HttpServletRequest request) {
        Browser browser = UserAgent.parseUserAgentString(browserAgent(request)).getBrowser();
        return browser.getName().toLowerCase();
    }

    /**
     * 获取访问的Host
     *
     * @param request the request
     * @return the string
     */
    public static String parseHost(HttpServletRequest request) {
        return request.getHeader("Host");
    }

    /**
     * 获取访问的Host，不要端口号
     *
     * @param request the request
     * @return the string
     */
    public static String parseHostWithoutPort(HttpServletRequest request) {
        return parseHost(request).replaceAll(":.*", "");
    }

    /**
     * 获取客户端IP
     *
     * @param request the request
     * @return the string
     */
    public static String parseClientIP(HttpServletRequest request) {
        for (String header : IP_HEADER_CANDIDATES) {
            String ip = request.getHeader(header);
            if (StringUtils.isNoneBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
                return ip;
            }
        }
        return request.getRemoteAddr();
    }

    private static String browserAgent(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        if (StringUtils.isBlank(userAgent)) {
            return "";
        }
        return userAgent;
    }

    /**
     * Parse device code string.
     *
     * @param request the request
     * @return the string
     */
    public static String parseDeviceCode(HttpServletRequest request) {
        try {
            if (request instanceof CustomRequestWrapper) {
                CustomRequestWrapper wrapper = (CustomRequestWrapper) request;
                String body = new String(wrapper.getBody());
                if (StringUtils.isNotBlank(body)) {
                    Map dataMap = JSON.parseObject(body, Map.class);
                    String deviceCode = (String) dataMap.get("uuid");
                    if (StringUtils.isBlank(deviceCode)) {
                        return "";
                    }
                    return deviceCode;
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "";
    }

    /**
     * Parse method string.
     *
     * @param request the request
     * @return the string
     */
    public static String parseMethod(HttpServletRequest request) {

        return request.getMethod().toUpperCase();
    }

    /**
     * Parse payload string.
     *
     * @param request the request
     * @return the string
     * @throws Exception the exception
     */
    public static String parsePayload(HttpServletRequest request) throws Exception {

        byte[] byteBody = IOUtils.toByteArray(request.getInputStream());
        String playload = "";
        if (byteBody == null) {
            playload = new String(byteBody);
        }
        return playload;
    }

    /**
     * Parse query string string.
     *
     * @param request the request
     * @return the string
     */
    public static String parseQueryString(HttpServletRequest request) {

        return request.getQueryString();
    }

    /**
     * Parse headers map.
     *
     * @param request the request
     * @return the map
     */
    public static Map<String, String> parseHeaders(HttpServletRequest request) {

        Map<String, String> headers = new TreeMap<>();
        Enumeration er = request.getHeaderNames();
        while (er.hasMoreElements()) {
            String name = (String) er.nextElement();
            String value = request.getHeader(name);
            if (StringUtils.isNotBlank(value)) {
                headers.put(name, value);
            }
        }
        return headers;
    }

    /**
     * Gets parameter string map.
     *
     * @param request the request
     * @return the parameter string map
     */
    public static Map<String, String> getParameterStringMap(HttpServletRequest request) {

        Map<String, String[]> properties = request.getParameterMap();
        Map<String, String> returnMap = new HashMap<String, String>();
        String name = "";
        String value = "";
        for (Map.Entry<String, String[]> entry : properties.entrySet()) {
            name = entry.getKey();
            String[] values = entry.getValue();
            if (null == values) {
                value = "";
            } else if (values.length > 1) {
                for (int i = 0; i < values.length; i++) {
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length() - 1);
            } else {
                value = values[0];
            }
            returnMap.put(name, value);

        }
        return returnMap;
    }


    /**
     * 判断是否是IP地址
     *
     * @return the parameter string map
     */
    public static boolean isCorrectIp(String ipString) {
        String ipRegex = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";
        //如果前三项判断都满足，就判断每段数字是否都位于0-255之间
        if (ipString.matches(ipRegex)) {
            String[] ipArray = ipString.split("\\.");
            try {
                for (int i = 0; i < ipArray.length; i++) {
                    int number = Integer.parseInt(ipArray[i]);
                    //4.判断每段数字是否都在0-255之间
                    if (number < 0 || number > 255) {
                        return false;
                    }
                }
            } catch (Exception e) {
                logger.error("isCorrectIp error ", e);
                return false;
            }
            return true;
        } else {
            return false;
        }
    }
}
