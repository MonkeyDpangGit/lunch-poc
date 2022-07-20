package com.test.domain.prometheus;

import io.prometheus.client.Counter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * PrometheusInterceptor
 *
 * @author torrisli
 * @date 2021/6/11
 * @Description: PrometheusInterceptor
 */
public class PrometheusInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        Counter counter = Counter.build().name(request.getRequestURI().replaceAll("/", "")).labelNames("code").help(request.getRequestURI())
                .register();
        counter.labels("ok").inc();

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

        Counter counter = Counter.build().name(request.getRequestURI()).labelNames("code").help(request.getRequestURI())
                .register();
        counter.labels(String.valueOf(response.getStatus())).inc();
    }
}
