package com.t3t.bookstoreapi.upload.response;

import com.t3t.bookstoreapi.upload.dto.AccessDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Object Storage의 response를 json으로 파싱하기 위한 DTO
 * @author Yujin-nKim(김유진)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponse {
    private AccessDto access;
}
