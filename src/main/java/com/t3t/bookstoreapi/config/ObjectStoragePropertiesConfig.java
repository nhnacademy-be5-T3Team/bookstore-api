package com.t3t.bookstoreapi.config;

import com.t3t.bookstoreapi.keymanager.service.SecretKeyManagerService;
import com.t3t.bookstoreapi.property.ObjectStorageProperties;
import com.t3t.bookstoreapi.property.SecretKeyProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Object Storage 연결에 필요한 속성을 설정하는 클래스
 * @author Yujin-nKim(김유진)
 */
@Configuration
public class ObjectStoragePropertiesConfig {

    /**
     * Object Storage 연결에 필요한 속성을 ObjectStorageProperties 객체에 설정하고 반환
     * @param secretKeyManagerService 시크릿 키 매니저 서비스
     * @param secretKeyProperties 시크릿 키 속성
     * @return Object Storage 연결에 필요한 속성이 정의된 객체 ObjectStorageProperties
     * @author Yujin-nKim(김유진)
     */
    @Bean
    public ObjectStorageProperties objectStorageProperties(SecretKeyManagerService secretKeyManagerService,
                                                           SecretKeyProperties secretKeyProperties) {
        return ObjectStorageProperties.builder()
                .authUrl(secretKeyManagerService.getSecretValue(secretKeyProperties.getObjectStorageAuthUrlKeyId()))
                .tenantId(secretKeyManagerService.getSecretValue(secretKeyProperties.getObjectStorageTenantIdKeyId()))
                .userName(secretKeyManagerService.getSecretValue(secretKeyProperties.getObjectStorageUserNameKeyId()))
                .password(secretKeyManagerService.getSecretValue(secretKeyProperties.getObjectStoragePasswordKeyId()))
                .build();
    }
}
