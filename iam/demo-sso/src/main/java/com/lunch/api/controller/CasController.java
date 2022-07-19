package com.lunch.api.controller;

import com.lunch.api.util.UrlUtil;
import com.lunch.api.util.RestTemplateUtil;
import java.util.Map;
import java.util.TreeMap;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author torrisli
 * @date 2021/1/4
 * @Description: TODO
 */
@Controller
@RequestMapping("/auth")
public class CasController {

    @RequestMapping(value = "/cas/validate", method = RequestMethod.GET)
    public void casLogin(
            @RequestParam(value = "validateUrl") String validateUrl,
            @RequestParam(value = "ticket") String ticket,
            HttpServletResponse response) throws Exception {

        StringBuilder url = new StringBuilder(validateUrl);
        if (validateUrl.indexOf("\\?") == -1) {
            url.append("?");
        } else {
            url.append("&");
        }
        url.append("ticket=");
        url.append(ticket);

        String[] urlGrp = validateUrl.split("/");
        String service = urlGrp[urlGrp.length -1];
        url.append("&service=");
        url.append(service);

        ResponseEntity<String> result = RestTemplateUtil.request(url.toString(), RequestMethod.GET, String.class);
        String body = result.getBody();

        Map<String, Object> output = new TreeMap<String, Object>();
        output.put("ticket", ticket);
        output.put("body", body);

        response.sendRedirect(UrlUtil.getRedirectUrl(output, "CAS票据验证"));
        return;
    }
}
