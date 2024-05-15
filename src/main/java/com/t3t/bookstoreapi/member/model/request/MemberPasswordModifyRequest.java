package com.t3t.bookstoreapi.member.model.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

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
    @NotBlank(message = "현재 비밀번호가 누락되었습니다.")
    private String currentPassword;
    @NotBlank(message = "새로운 비밀번호가 누락되었습니다.")
    private String newPassword;
}
