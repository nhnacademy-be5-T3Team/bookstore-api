package com.t3t.bookstoreapi.upload.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 토큰의 id와 만료 기간을 포함하는 데이터 전송 객체(DTO)
 * @author Yujin-nKim(김유진)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenDto {
    private String id;
    private String expires;
}
