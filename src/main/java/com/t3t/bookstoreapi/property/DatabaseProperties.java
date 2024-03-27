package com.t3t.bookstoreapi.property;

import lombok.*;

/**
 * 데이터베이스 관련 속성을 저장하는 프로퍼티 클래스
 * @author woody35545(구건모)
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DatabaseProperties {
    private String databaseUrl;
    private String driverClassName;
    private String username;
    private String password;
}
