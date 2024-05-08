package com.t3t.bookstoreapi.certcode.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

/**
 * 인증 코드 엔티티
 * Redis 에 저장되며 5 분간 유지된다.
 *
 * @author woody35545(구건모)
 */
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "cert", timeToLive = 60 * 5)
public class CertCode {
    @Id
    private Long memberId;
    private String code;
}
