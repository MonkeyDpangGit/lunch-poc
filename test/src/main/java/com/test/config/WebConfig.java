package com.test.config;

import com.test.domain.prometheus.PrometheusInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebConfig
 *
 * @author torrisli
 * @date 2021/6/11
 * @Description: WebConfig
 */
//@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new PrometheusInterceptor());
    }
}
