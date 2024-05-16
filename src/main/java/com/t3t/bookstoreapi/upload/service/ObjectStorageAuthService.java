package com.t3t.bookstoreapi.upload.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.t3t.bookstoreapi.property.ObjectStorageProperties;
import com.t3t.bookstoreapi.upload.exception.InvalidJsonProcessingException;
import com.t3t.bookstoreapi.upload.request.TokenRequest;
import com.t3t.bookstoreapi.upload.response.TokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * ObjectStorage의 인증을 위한 클래스
 * @author Yujin-nKim(김유진)
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ObjectStorageAuthService {
    private final ObjectStorageProperties objectStorageProperties;
    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;
    private final RedisTemplate<String, String> redisTemplate;
    private final String OBJECT_STORAGE_REQUEST_TOKEN_ID = "ObjectStorageRequestTokenId";

    /**
     * ObjectStorage로 토큰 생성 요청을 보내기 위한 TokenRequest를 생성
     * @return 생성된 토큰 생성 요청 객체
     * @author Yujin-nKim(김유진)
     */
    public TokenRequest createTokenRequest() {
        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.getAuth().setTenantId(objectStorageProperties.getTenantId());
        tokenRequest.getAuth().getPasswordCredentials().setUsername(objectStorageProperties.getUserName());
        tokenRequest.getAuth().getPasswordCredentials().setPassword(objectStorageProperties.getPassword());
        return  tokenRequest;
    }

    /**
     * ObjectStorage로 토큰을 요청하는 메서드
     * @param tokenRequest 토큰 요청 객체
     * @return 받은 토큰 응답 객체
     * @throws InvalidJsonProcessingException JSON 처리 예외가 발생할 경우
     * @author Yujin-nKim(김유진)
     */
    public TokenResponse requestToken(TokenRequest tokenRequest) {
        String identityUrl = objectStorageProperties.getAuthUrl() + "/tokens";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        HttpEntity<TokenRequest> httpEntity = new HttpEntity<>(tokenRequest, headers);
        ResponseEntity<String> response = this.restTemplate.exchange(identityUrl, HttpMethod.POST, httpEntity, String.class);

        TokenResponse token;
        try {
            token = mapper.readValue(response.getBody(), TokenResponse.class);
        } catch (JsonProcessingException ex) {
            throw new InvalidJsonProcessingException(ex);
        }
        return token;
    }

    /**
     * 인증 토큰을 가져오는 메서드 <br>
     * Redis에서 토큰을 조회하고 없으면 생성하여 Redis에 저장한 후 토큰 id를 반환
     * @return 가져온 토큰 id
     * @author Yujin-nKim(김유진)
     */
    public String getToken() {

        try {
            ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

            // redis에 토큰이 존재하는지 확인
            String tokenId = valueOperations.get(OBJECT_STORAGE_REQUEST_TOKEN_ID);
            if (Objects.nonNull(tokenId)) {
                log.info("토큰이 존재합니다. = {}", tokenId);
                return tokenId;
            }

            // 토큰 생성 요청
            TokenResponse token = requestToken(createTokenRequest());
            String responseTokenId = token.getAccess().getToken().getId();
            String responseTokenExpires = token.getAccess().getToken().getExpires();

            // Redis에 token Id 저장
            valueOperations.set(OBJECT_STORAGE_REQUEST_TOKEN_ID, responseTokenId);

            ZonedDateTime expiryTime = ZonedDateTime.parse(responseTokenExpires);
            Duration duration = Duration.between(ZonedDateTime.now(), expiryTime);
            long expirationSeconds = duration.getSeconds();

            //  Redis에 저장된 토큰의 만료 시간을 생성한 토큰의 만료 시간과 동일하게 설정
            redisTemplate.expire(OBJECT_STORAGE_REQUEST_TOKEN_ID, expirationSeconds, TimeUnit.SECONDS);

            return responseTokenId;
        } catch (RedisConnectionFailureException ex) {
            log.error("Redis 연결 실패: {}", ex.getMessage());
            throw ex;
        }
    }
}
