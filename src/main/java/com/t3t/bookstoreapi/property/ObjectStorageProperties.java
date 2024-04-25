package com.t3t.bookstoreapi.property;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ObjectStorageProperties {
    private String authUrl;
    private String tenantId;
    private String userName;
    private String password;
}
