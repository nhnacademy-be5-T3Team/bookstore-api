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

/**
 * Redis 서비스를 구성하기 위한 클래스
 * @author Yujin-nKim(김유진)
 */
@Configuration
public class RedisConfig {
    /**
     * Redis 서비스에 연결하기 위한 속성을 설정 <br>
     * RedisProperties 객체를 생성하고 설정한 속성을 포함하여 반환
     * @param secretKeyManagerService 시크릿 키 매니저 서비스
     * @param secretKeyProperties 시크릿 키 속성
     * @return RedisProperties
     * @author Yujin-nKim(김유진)
     */
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

    /**
     * RedisConnectionFactory 객체를 생성하고 설정한 RedisStandaloneConfiguration을 적용하여 Redis 서버와의 연결을 설정
     * @param redisProperties Redis 서버 연결에 필요한 속성
     * @return RedisConnectionFactory
     * @author Yujin-nKim(김유진)
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory(RedisProperties redisProperties) {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(redisProperties.getRedisServerIpAddress());
        redisStandaloneConfiguration.setPort(Integer.parseInt(redisProperties.getRedisServerPort()));
        redisStandaloneConfiguration.setPassword(redisProperties.getRedisServerPassword());
        redisStandaloneConfiguration.setDatabase(21);
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    /**
     * Redis 서버와 통신하기 위한 RedisTemplate을 생성하여 반환
     * @param redisProperties Redis 서비스에 필요한 속성
     * @return RedisTemplate
     * @author Yujin-nKim(김유진)
     */
    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisProperties redisProperties) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory(redisProperties));
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        return redisTemplate;
    }
}
