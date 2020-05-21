package com.lits.userservice.config;

import feign.Logger;
import feign.Request;
import feign.RequestInterceptor;
import feign.Retryer;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.TimeUnit;

public class FeignConfig {
    @Bean
    public Logger.Level configureLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public Request.Options timeoutConfiguration() {
        return new Request.Options(5000, TimeUnit.MILLISECONDS, 3000, TimeUnit.MILLISECONDS, false);
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> requestTemplate.header("test", "test");
    }

    @Bean
    public Retryer retryer() {
        return new Retryer.Default(1000, 800, 3);
    }
}
