package com.t3t.bookstoreapi.upload.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 토큰 정보를 포함하는 데이터 전송 객체(DTO)
 * @author Yujin-nKim(김유진)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccessDto {
    private TokenDto token;
}
