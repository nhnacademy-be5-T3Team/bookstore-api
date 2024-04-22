package com.t3t.bookstoreapi.member.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 회원 주소 생성 요청 객체
 *
 * @author woody35545(구건모)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberAddressCreationRequest {
    @NotNull(message = "회원 식별자가 누락되었습니다.")
    private Long memberId;
    @NotNull(message = "우편 주소가 누락되었습니다.")
    private Integer addressNumber;
    @NotBlank(message = "도로명 주소가 누락되었습니다.")
    private String roadNameAddress;
    @NotBlank(message = "주소 별칭이 누락되었습니다.")
    private String addressNickname;
    @NotBlank(message = "상세 주소가 누락되었습니다.")
    private String addressDetail;
}
