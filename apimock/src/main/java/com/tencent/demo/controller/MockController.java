package com.tencent.demo.controller;

import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * MockController
 *
 * @author torrisli
 * @date 2024/4/18
 * @Description: MockController
 */
@RestController
public class MockController {

    @GetMapping("/sgid-sso/login")
    public void redirect(@RequestParam String service, HttpServletResponse response) throws IOException {

        System.out.println("[redirect] " + service);

        response.sendRedirect(service
                + "?province=1&encryptKey=2&loginType=3&execution=qwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjkqwiksdfghjksdfghjk&_eventId=5&errmsg=");
        return;
    }

    @PostMapping("/sgid-sso/login")
    public void formSubmit(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Map<String, String[]> parameterMap = request.getParameterMap();
        System.out.println("[formSubmit] " + parameterMap.toString());
        String service = parameterMap.get("service")[0];

        response.sendRedirect(service + "?ticket=ticket_qwertyuiop1234567890");
        return;
    }

    @GetMapping("/sgid-sso/serviceValidate")
    public String serviceValidate(HttpServletRequest request, HttpServletResponse response) {

        String queryString = request.getQueryString();
        System.out.println("[serviceValidate] " + queryString);

        String responseBody = "<cas:serviceResponse xmlns:cas='http://www.yale.edu/tp/cas'>\n"
                + "    <cas:authenticationSuccess>     \n"
                + "\t\t<cas:user>{\"passWord\":null,\"iscUserSourceId\":\"yanjue\",\"logintime\":1634113231179,\"iscAdCode\":\"yanjue\",\"scop\":0,\"baseOrgId\":\"1\",\"name\":\"yanjue\",\"iscUserId\":\"e670957f81ea4164afee8ead4a32e968\"}</cas:user>\n"
                + "\t\t<cas:attributes>\n"
                + "\t\t\t<cas:iscUserId>proxyuser001</cas:iscUserId>\n"
                + "\t\t</cas:attributes>\n"
                + "        </cas:authenticationSuccess>\n"
                + "</cas:serviceResponse>";
        return responseBody;
    }
}
