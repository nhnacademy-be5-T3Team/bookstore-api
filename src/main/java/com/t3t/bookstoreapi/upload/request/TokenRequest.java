package com.t3t.bookstoreapi.upload.request;

import lombok.Data;

/**
 * Object Storage로 토큰 생성 요청을 보내기 위한 데이터 전송 객체(DTO)
 * @author Yujin-nKim(김유진)
 */
@Data
public class TokenRequest {

    private Auth auth = new Auth();

    @Data
    public class Auth {
        private String tenantId;
        private PasswordCredentials passwordCredentials = new PasswordCredentials();
    }

    @Data
    public class PasswordCredentials {
        private String username;
        private String password;
    }
}