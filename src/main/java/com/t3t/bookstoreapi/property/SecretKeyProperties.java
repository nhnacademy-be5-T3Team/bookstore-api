package com.t3t.bookstoreapi.property;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Secret Key Manager 에 등록된 기밀 데이터의 key id를 저장하는 프로퍼티 클래스
 * @author woody35545(구건모)
 */
@Profile("!local")
@Getter
@Component
public class SecretKeyProperties {
    @Value("${t3t.secretKeyManager.secrets.databaseServerIpAddress.keyId}")
    private String databaseIpAddressKeyId;
    @Value("${t3t.secretKeyManager.secrets.databaseServerPort.keyId}")
    private String databasePortKeyId;
    @Value("${t3t.secretKeyManager.secrets.databaseServerUsername.keyId}")
    private String databaseNameKeyId;
    @Value("${t3t.secretKeyManager.secrets.databaseName.keyId}")
    private String databaseUsernameKeyId;
    @Value("${t3t.secretKeyManager.secrets.databaseServerPassword.keyId}")
    private String databasePasswordKeyId;
    @Value("${t3t.secretKeyManager.secrets.objectStorageTenantId.keyId}")
    private String objectStorageTenantIdKeyId;
    @Value("${t3t.secretKeyManager.secrets.objectStorageUserName.keyId}")
    private String objectStorageUserNameKeyId;
    @Value("${t3t.secretKeyManager.secrets.objectStoragePassword.keyId}")
    private String objectStoragePasswordKeyId;
    @Value("${t3t.secretKeyManager.secrets.objectStorageAuthUrl.keyId}")
    private String objectStorageAuthUrlKeyId;
    @Value("${t3t.secretKeyManager.secrets.redisServerIpAddress.keyId}")
    private String redisServerIpAddressKeyId;
    @Value("${t3t.secretKeyManager.secrets.redisServerPort.keyId}")
    private String redisServerPortKeyId;
    @Value("${t3t.secretKeyManager.secrets.redisServerPassword.keyId}")
    private String redisServerPasswordKeyId;
    @Value("${t3t.secretKeyManager.secrets.tossWidgetSecretKey.keyId}")
    private String tossWidgetSecretKeyKeyId;
}
