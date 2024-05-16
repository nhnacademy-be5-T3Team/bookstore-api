package com.t3t.bookstoreapi.payment.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.t3t.bookstoreapi.payment.constant.TossPaymentStatus;
import lombok.*;

import java.time.LocalDateTime;


/**
 * 결제 서비스 제공자의 결제 승인 응답을 담는 클래스
 *
 * @author woody35545(구건모)
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentConfirmResponse {
    private String paymentKey; // 결제 제공자 측의 결제 식별자
    private String orderId; // 결제 제공자 측의 사용하는 주문 식별자
    private String status; // 결제 제공자 측의 결제 상태
    private Receipt receipt; // 결제 제공자 측의 영수증 정보
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private LocalDateTime requestedAt; // 결제 제공자 측에 기록된 요청 시간
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private LocalDateTime approvedAt;  // 결제 제공자 측의 결제 승인 시간

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Receipt {
        private String url; // 결제 제공자측의 영수증 주소
    }
}
