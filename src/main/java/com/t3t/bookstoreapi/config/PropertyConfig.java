package com.t3t.bookstoreapi.config;

import com.t3t.bookstoreapi.keymanager.service.SecretKeyManagerService;
import com.t3t.bookstoreapi.property.DatabaseProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @apiNote 프로퍼티 클래스들을 profile 에 따라 정의하기 위한 프로퍼티 설정 클래스
 * @author woody35545(구건모)
 */
@Configuration
@RequiredArgsConstructor
public class PropertyConfig {
    private final SecretKeyManagerService secretKeyManagerService;

    @Value("${t3t.secretKeyManager.secrets.databaseServerIpAddress.keyId}")
    private String databaseIpAddressKeyId;

    @Value("${t3t.secretKeyManager.secrets.databaseServerPort.keyId}")
    private String databasePortKeyId;

    @Value("${t3t.secretKeyManager.secrets.databaseServerUsername.keyId}")
    private String databaseUsernameKeyId;

    @Value("${t3t.secretKeyManager.secrets.databaseServerPassword.keyId}")
    private String databasePasswordKeyId;

    @Value("${t3t.secretKeyManager.secrets.databaseName.keyId}")
    private String databaseNameKeyId;


    /**
     * production 환경에서 사용할 DatabaseProperties 빈 등록
     * @return DatabaseProperties
     * @author woody35545(구건모)
     */
    @Bean
    @Profile("prod")
    public DatabaseProperties devDataSourceProperties() {
        return DatabaseProperties.builder()
                .databaseUrl(String.format("jdbc:mysql://%s:%s/%s",
                        secretKeyManagerService.getSecretValue(databaseIpAddressKeyId),
                        secretKeyManagerService.getSecretValue(databasePortKeyId),
                        secretKeyManagerService.getSecretValue(databaseNameKeyId)))
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .username(secretKeyManagerService.getSecretValue(databaseUsernameKeyId))
                .password(secretKeyManagerService.getSecretValue(databasePasswordKeyId))
                .build();
    }
}
