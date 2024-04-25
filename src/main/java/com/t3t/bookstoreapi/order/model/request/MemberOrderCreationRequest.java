package com.t3t.bookstoreapi.order.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 회원 주문 생성 요청 객체
 * @auhtor woody35545(구건모)
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberOrderCreationRequest {

    @NotNull(message = "회원 식별자가 누락되었습니다.")
    private Long memberId;

    @NotNull(message = "배송 정보가 누락되었습니다.")
    private Long deliveryId;
}

