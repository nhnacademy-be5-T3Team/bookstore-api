package com.t3t.bookstoreapi.order.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 회원 임시 주문 생성에 대한 응답 객체
 *
 * @auhtor woody35545(구건모)
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberOrderPreparationResponse {
    /**
     * 회원 정보
     */
    private Long memberId; // 회원 식별자

    /**
     * 생성된 주문 정보
     */
    private Long orderId; // 주문 식별자

    /**
     * 결제 관련 정보
     */
    private String providerOrderId; // 결제 제공자측에서 사용할 주문 식별자
    private BigDecimal totalPrice; // 총 결제해야 할 금액

    /**
     * 배송 정보
     */
    private Long deliveryId; // 배송 정보 식별자
}
