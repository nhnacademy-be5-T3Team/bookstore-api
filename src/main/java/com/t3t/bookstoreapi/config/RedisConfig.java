package com.t3t.bookstoreapi.config;

import com.t3t.bookstoreapi.keymanager.service.SecretKeyManagerService;
import com.t3t.bookstoreapi.property.RedisProperties;
import com.t3t.bookstoreapi.property.SecretKeyProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfig {
    @Bean
    public RedisProperties redisProperties(SecretKeyManagerService secretKeyManagerService,
                                           SecretKeyProperties secretKeyProperties) {
        return RedisProperties.builder()
                .redisServerIpAddress(secretKeyManagerService.getSecretValue(secretKeyProperties.getRedisServerIpAddressKeyId()))
                .redisServerPort(secretKeyManagerService.getSecretValue(secretKeyProperties.getRedisServerPortKeyId()))
                .redisServerPassword(secretKeyManagerService.getSecretValue(secretKeyProperties.getRedisServerPasswordKeyId()))
                .build();
    }
}
