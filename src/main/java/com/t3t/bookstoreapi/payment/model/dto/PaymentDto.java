package com.t3t.bookstoreapi.payment.model.dto;

import com.t3t.bookstoreapi.payment.model.entity.Payment;
import com.t3t.bookstoreapi.payment.model.entity.PaymentProvider;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 결제 DTO 클래스<br>
 * 결제 서비스 제공자에 따른 결제의 서브타입 정보도 포함한다.
 *
 * @author woody35545(구건모)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {
    /**
     * 결제 정보
     */
    private Long paymentId;
    private Long orderId;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;

    /**
     * 결제 제공자 정보
     */
    private PaymentProvider paymentProvider;
    private String providerPaymentKey;
    private String providerOrderId;
    private String providerPaymentStatus;
    private String providerPaymentReceiptUrl;
    private LocalDateTime providerPaymentRequestedAt;
    private LocalDateTime providerPaymentApprovedAt;
}
