package com.lunch.oauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * OAuth2ServerApp
 *
 * @author torrisli
 * @date 2023/1/29
 * @Description: OAuth2ServerApp
 * <p>
 * 1、spring-security:
 * https://blog.csdn.net/guorui_java/article/details/118229097
 * 2、spring-security-oauth2-authorization-server:
 * https://zhuanlan.zhihu.com/p/538443308
 * https://blog.csdn.net/qq_36740038/article/details/126743251
 * https://docs.spring.io/spring-authorization-server/docs/0.3.1/reference/html/getting-started.html
 * </p>
 */
@SpringBootApplication
public class OAuth2ServerApp {

    public static void main(String[] args) {

        SpringApplication.run(OAuth2ServerApp.class, args);
    }
}
