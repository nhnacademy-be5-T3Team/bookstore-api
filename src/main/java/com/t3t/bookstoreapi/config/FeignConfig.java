package com.t3t.bookstoreapi.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages ="com.t3t.bookstoreapi")
public class FeignConfig {
}
