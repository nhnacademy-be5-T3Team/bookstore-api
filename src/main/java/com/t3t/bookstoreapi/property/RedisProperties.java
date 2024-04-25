package com.t3t.bookstoreapi.property;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RedisProperties {
    private String redisServerIpAddress;
    private String redisServerPort;
    private String redisServerPassword;
}
