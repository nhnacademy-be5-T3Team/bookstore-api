package com.t3t.bookstoreapi.property;

import lombok.Builder;
import lombok.Getter;

/**
 * Redis 서버에 연결하기 위해 필요한 속성을 저장하는 프로퍼티 클래스
 * @author Yujin-nKim(김유진)
 */
@Getter
@Builder
public class RedisProperties {
    private String redisServerIpAddress;
    private String redisServerPassword;
    private String redisServerPort;
    private Integer redisDatabase;
}
