package com.t3t.bookstoreapi.config;

import com.t3t.bookstoreapi.keymanager.service.SecretKeyManagerService;
import com.t3t.bookstoreapi.property.ObjectStorageProperties;
import com.t3t.bookstoreapi.property.SecretKeyProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectStoragePropertiesConfig {

    @Bean
    public ObjectStorageProperties objectStorageProperties(SecretKeyManagerService secretKeyManagerService,
                                                           SecretKeyProperties secretKeyProperties) {
        return ObjectStorageProperties.builder()
                .authUrl(secretKeyManagerService.getSecretValue(secretKeyProperties.getObjectStorageAuthUrl()))
                .tenantId(secretKeyManagerService.getSecretValue(secretKeyProperties.getObjectStorageTenantIdKeyId()))
                .userName(secretKeyManagerService.getSecretValue(secretKeyProperties.getObjectStorageUserNameKeyId()))
                .password(secretKeyManagerService.getSecretValue(secretKeyProperties.getObjectStoragePasswordKeyId()))
                .build();
    }
}
