package com.t3t.bookstoreapi.member.model.request;

import lombok.*;

/**
 * 회원 비밀번호 수정 요청 객체
 *
 * @author woody35545(구건모)
 */
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberPasswordModifyRequest {
    private String currentPassword;
    private String newPassword;
}
