package com.tencent.common;

import java.util.TimeZone;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

/**
 * CommonApplication
 *
 * @author torrisli
 * @date 2021/8/23
 * @Description: CommonApplication
 */
@SpringBootApplication
@EnableMongoAuditing
public class CommonApplication {

    public static void main(String[] args) {

        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Shanghai"));
        SpringApplication application = new SpringApplication(CommonApplication.class);
        application.run(args);
    }
}
