package com.test;

import com.tencent.rainbow.annotation.EnableRainbowConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * App
 *
 * @author torrisli
 * @date 2021/6/11
 * @Description: App
 */
@SpringBootApplication
@EnableRainbowConfig("admin")
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
