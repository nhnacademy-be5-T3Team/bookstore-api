package com.t3t.bookstoreapi.property;

import lombok.Builder;
import lombok.Getter;

/**
 * Object Storage에 연결하기 위해 필요한 속성을 저장하는 프로퍼티 클래스
 * @author Yujin-nKim(김유진)
 */
@Getter
@Builder
public class ObjectStorageProperties {
    private String storageUrl;
    private String authUrl;
    private String tenantId;
    private String userName;
    private String password;
}