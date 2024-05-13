package com.t3t.bookstoreapi.config;

import com.nhn.dooray.client.DoorayHookSender;
import com.t3t.bookstoreapi.property.MessageSenderProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class MessageSenderConfig {
    /**
     * 메시지 전송을 위한 DoorayHookSender 빈
     *
     * @author woody35545(구건모)
     */
    @Bean
    public DoorayHookSender messageSender(RestTemplate restTemplate, MessageSenderProperties properties) {
        return new DoorayHookSender(restTemplate, properties.getHookUrl());
    }
}
