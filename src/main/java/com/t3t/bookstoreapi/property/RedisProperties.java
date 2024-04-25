package com.t3t.bookstoreapi.property;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RedisProperties {
    private String redisServerIpAddress;
    private String redisServerPassword;
    private String redisServerPort;
    private Integer redisDatabase;
}
