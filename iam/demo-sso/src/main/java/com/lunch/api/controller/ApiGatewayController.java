package com.lunch.api.controller;

import com.lunch.api.util.RestTemplateUtil;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * ApiGatewayController
 *
 * @author torrisli
 * @date 2021/3/7
 * @Description: ApiGatewayController
 */
@RestController
@RequestMapping("/apigateway")
public class ApiGatewayController {

    private static final String clientId = "ZGNhNzU4ODBkOWQ1NDY5MTgxMDRlNmYyMWM3ZWE0Zjk";
    private static final String clientSecret = "2UWnXya87fnpWc7Mgkf63rRd6xNhJ3cB";
    private static final String grantType = "authorization_code";
    private static final String responseType = "code";
    private static final String authorizeApiRedirectUrl = "http://t1.portal.idaas.com:8002/auth/oauth2/authorize";
    private static final String tokenApiFrontUrl = "http%3A%2F%2Flocalhost%3A9080%2Fapigateway%2Ftoken";
    private static final String tokenApiBackUrl = "http://t1.portal.idaas.com:8002/auth/oauth2/token";
    private static final String workApiFrontUrl = "http%3A%2F%2Flocalhost%3A9080%2Fapigateway%2Fwork";

    @RequestMapping(value = "/token", method = RequestMethod.GET)
    public String token(HttpServletRequest request, HttpServletResponse response) throws Exception {

        StringBuilder tokenUrl = new StringBuilder(tokenApiBackUrl);

        tokenUrl.append("?code=").append(request.getParameter("code"));
        tokenUrl.append("&client_id=").append(clientId);
        tokenUrl.append("&client_secret=").append(URLEncoder.encode(clientSecret, "UTF-8"));
        tokenUrl.append("&grant_type=").append(grantType);
        tokenUrl.append("&redirect_uri=").append(URLEncoder.encode(request.getParameter("redirect_uri"), "UTF-8"));

        ResponseEntity<String> result = RestTemplateUtil
                .request(tokenUrl.toString(), RequestMethod.POST, String.class);
        if (result.getStatusCodeValue() == 302) {
            HttpHeaders headers = result.getHeaders();
            headers.forEach((k, v) -> {
                response.setHeader(k, v.get(0));
            });
            List locations = headers.get("location");
            response.sendRedirect(locations.get(0).toString());
            return "";
        } else {
            String body = result.getBody();
            return body;
        }
    }

    @RequestMapping(value = "/work", method = RequestMethod.GET)
    public String work(HttpServletRequest request, HttpServletResponse response) throws IOException {

        boolean idTokenExist = false;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if ("id_token".equals(cookie.getName())) {
                idTokenExist = true;
                break;
            }
        }

        if (!idTokenExist) {
            StringBuilder authorizeUrl = new StringBuilder(authorizeApiRedirectUrl);
            authorizeUrl.append("?client_id=").append(clientId);
            authorizeUrl.append("&response_type=").append(responseType);
            authorizeUrl.append("&token_redirect_uri=").append(tokenApiFrontUrl);
            authorizeUrl.append("&redirect_uri=").append(workApiFrontUrl);
            authorizeUrl.append("&state=").append("8dfd5502-25f4-415f-9218-9b2e05fda48b");

            response.sendRedirect(authorizeUrl.toString());
            return "";
        } else {
            StringBuilder result = new StringBuilder();
            result.append("Hello World!\r\n");
            result.append("Cookie:\r\n");
            for (Cookie cookie : request.getCookies()) {
                result.append(cookie.getName());
                result.append("=");
                result.append(cookie.getValue());
                result.append("\r\n");
            }

            return result.toString();
        }
    }
}
