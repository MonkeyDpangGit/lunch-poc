package com.lunch;

import java.util.TimeZone;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * CorsDemoApp
 *
 * @author torrisli
 * @date 2021/10/27
 * @Description: CorsDemoApp
 */
@SpringBootApplication
public class CorsDemoApp {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        SpringApplication application = new SpringApplication(CorsDemoApp.class);
        application.run(args);
    }
}
