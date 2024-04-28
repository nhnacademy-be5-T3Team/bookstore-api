package com.t3t.bookstoreapi.config;

import com.t3t.bookstoreapi.keymanager.service.SecretKeyManagerService;
import com.t3t.bookstoreapi.property.SecretKeyProperties;
import com.t3t.bookstoreapi.property.TossPaymentProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Toss 결제 관련 프로퍼티 설정 클래스<br>
 * SecretKeyManagerService 를 통해 토스 결제 서비스에서 사용되는 프로퍼티를 설정한다.
 * @see com.t3t.bookstoreapi.keymanager.service.SecretKeyManagerService
 * @see com.t3t.bookstoreapi.property.TossPaymentProperties
 * @author woody35545(구건모)
 */
@Configuration
public class TossPaymentPropertiesConfig {

    @Bean
    public TossPaymentProperties tossPaymentProperties(SecretKeyManagerService secretKeyManagerService,
                                                       SecretKeyProperties secretKeyProperties) {
        return TossPaymentProperties.builder()
                .widgetSecretKey(secretKeyManagerService.getSecretValue(secretKeyProperties.getTossWidgetSecretKeyKeyId()))
                .build();
    }
}
