package com.lunch.api.controller;

import com.alibaba.fastjson.JSON;
import com.lunch.api.util.UrlUtil;
import com.lunch.api.util.RestTemplateUtil;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * OAuthController
 *
 * @author torrisli
 * @date 2021/1/4
 * @Description: OAuthController
 */
@Controller
@RequestMapping("/auth")
public class OAuthController {

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public void index(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String authorizeUrl = "http://auth.portal.demo.tencentidentity.com:8002/auth/oauth2/authorize?client_id=YjgxNGU4MjBkMGUxNDQzYWIzOTQyNDc4ZjdhNjMyOWI&response_type=token&redirect_uri=http%3A%2F%2Fmy.app.com%3A9080%2Fauth%2Fredirect%2Fget&state=45722179-b98b-4d52-9119-86c1ee7231aa";
        response.sendRedirect(authorizeUrl);
        return;
    }

    @RequestMapping(value = "/oauth/accessToken", method = RequestMethod.POST)
    public void accessToken(@RequestParam(value = "requestUrl") String requestUrl,
            @RequestParam(value = "clientId") String clientId,
            @RequestParam(value = "clientSecret") String clientSecret,
            @RequestParam(value = "grantType") String grantType,
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "redirectUri", required = false) String redirectUri,
            @RequestParam(value = "refreshToken", required = false) String refreshToken,
            @RequestParam(value = "codeVerifier", required = false) String codeVerifier,
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "password", required = false) String password,
            @RequestParam(value = "bindingCode", required = false) String bindingCode,
            HttpServletResponse response) throws Exception {

        StringBuffer url = new StringBuffer(requestUrl);
        if (requestUrl.indexOf("\\?") == -1) {
            url.append("?");
        } else {
            url.append("&");
        }
        url.append("client_id=").append(clientId);
        url.append("&client_secret=").append(URLEncoder.encode(clientSecret, "UTF-8"));
        url.append("&grant_type=").append(grantType);
        url.append("&code=").append(code);
        url.append("&redirect_uri=").append(URLEncoder.encode(redirectUri, "UTF-8"));
        url.append("&refresh_token=").append(refreshToken);
        url.append("&code_verifier=").append(codeVerifier);
        url.append("&username=").append(username);
        url.append("&password=").append(URLEncoder.encode(password, "UTF-8"));
        url.append("&binding_code=").append(bindingCode);

        ResponseEntity<String> result = RestTemplateUtil.request(url.toString(), RequestMethod.POST, String.class);
        String body = result.getBody();

        Map<String, Object> output = JSON.parseObject(body, Map.class);

//        Map<String, Object> output = new TreeMap<String, Object>();
//        output.put("body", body);

        response.sendRedirect(UrlUtil.getRedirectUrl(output, "OAuth获取access_token"));
        return;
    }

}
