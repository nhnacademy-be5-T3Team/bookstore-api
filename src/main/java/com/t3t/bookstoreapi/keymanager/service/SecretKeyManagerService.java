package com.t3t.bookstoreapi.keymanager.service;

import com.t3t.bookstoreapi.keymanager.SecretKeyManagerApiRequestFailedException;
import com.t3t.bookstoreapi.property.SecretKeyManagerProperties;
import com.t3t.bookstoreapi.keymanager.model.response.SecretKeyManagerResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Secret Key Manager 에 등록된 Secret 값을 가져오기 위한 서비스 클래스
 *
 * @author woody35545(구건모)
 */
@Profile("!local")
@Slf4j
@Service
@RequiredArgsConstructor
public class SecretKeyManagerService {
    @Qualifier("sslRestTemplate")
    private final RestTemplate sslRestTemplate;
    private final SecretKeyManagerProperties secretKeyManagerProperties;

    private static final ParameterizedTypeReference<SecretKeyManagerResponse> secretKeyManagerResponseTypeReference
            = new ParameterizedTypeReference<SecretKeyManagerResponse>() {
    };

    /**
     * Secret Key Manager 에서 Secret 값 조회
     *
     * @param keyId 조회할 Key ID(Secret Key Manager 에 등록된 기밀 데이터의 Key ID)
     * @return Secret Key Manager 에서 조회한 Secret 값을 String 형태로 반환
     * @author woody35545(구건모)
     */
    public String getSecretValue(String keyId) {

        HttpEntity<SecretKeyManagerResponse> response =
                sslRestTemplate.exchange("https://api-keymanager.nhncloudservice.com/keymanager/v1.0/appkey/{appKey}/secrets/{keyId}",
                        HttpMethod.GET, null, secretKeyManagerResponseTypeReference,
                        secretKeyManagerProperties.getAppKey(), keyId);

        SecretKeyManagerResponse responseBody = response.getBody();

        if (responseBody == null) {
            throw new SecretKeyManagerApiRequestFailedException("Response body is null.");
        }

        if (responseBody.getHeader() == null || responseBody.getBody() == null || !responseBody.getHeader().getIsSuccessful().equals("true") || responseBody.getBody().getSecret() == null) {
            log.error("Secret Key Manager API response: {}", responseBody);
            throw new SecretKeyManagerApiRequestFailedException(String.format("Fail to request Secret Key Manager API (Key ID: %s)", keyId));
        }

        return responseBody.getBody().getSecret();
    }
}
