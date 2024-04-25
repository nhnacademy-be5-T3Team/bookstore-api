package com.t3t.bookstoreapi.config;

import com.t3t.bookstoreapi.keymanager.service.SecretKeyManagerService;
import com.t3t.bookstoreapi.property.RedisProperties;
import com.t3t.bookstoreapi.property.SecretKeyProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisProperties redisProperties(SecretKeyManagerService secretKeyManagerService,
                                           SecretKeyProperties secretKeyProperties) {
        return RedisProperties.builder()
                .redisServerIpAddress(secretKeyManagerService.getSecretValue(secretKeyProperties.getRedisServerIpAddressKeyId()))
                .redisServerPort(secretKeyManagerService.getSecretValue(secretKeyProperties.getRedisServerPortKeyId()))
                .redisServerPassword(secretKeyManagerService.getSecretValue(secretKeyProperties.getRedisServerPasswordKeyId()))
                .redisDatabase(21)
                .build();
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory(RedisProperties redisProperties) {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(redisProperties.getRedisServerIpAddress());
        redisStandaloneConfiguration.setPort(Integer.parseInt(redisProperties.getRedisServerPort()));
        redisStandaloneConfiguration.setPassword(redisProperties.getRedisServerPassword());
        redisStandaloneConfiguration.setDatabase(21);
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisProperties redisProperties) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory(redisProperties));
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        return redisTemplate;
    }
}
