package com.lunch.api.controller;

import com.lunch.api.util.UrlUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
public class CommonController {

    @RequestMapping(value = "/redirect/get", method = RequestMethod.GET)
    public void redirectGet(HttpServletRequest request, HttpServletResponse response) throws Exception {

        response.sendRedirect(UrlUtil.getRedirectUrl(request));
        return;
    }

    @RequestMapping(value = "/redirect/post", method = RequestMethod.POST)
    public void redirectPost(HttpServletRequest request, HttpServletResponse response) throws Exception {

        response.sendRedirect(UrlUtil.getRedirectUrl(request));
        return;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public void login(@RequestParam(value = "loginUrl") String loginUrl, HttpServletResponse response)
            throws Exception {

        response.sendRedirect(loginUrl);
        return;
    }
}
