package com.t3t.bookstoreapi.payment.model.response;

import com.t3t.bookstoreapi.payment.constant.TossPaymentStatus;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;


/**
 * 결제 서비스 제공자의 결제 승인 응답을 담는 클래스
 *
 * @author woody35545(구건모)
 */
@Builder
@Getter
public class PaymentConfirmResponse {
    private String providerPaymentKey;
    private String providerOrderId;
    private TossPaymentStatus providerPaymentStatus;
    private String providerPaymentReceiptUrl;
    private LocalDateTime providerPaymentRequestedAt;
    private LocalDateTime providerPaymentApprovedAt;
}
