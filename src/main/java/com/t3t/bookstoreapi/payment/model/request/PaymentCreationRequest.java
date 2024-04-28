package com.t3t.bookstoreapi.payment.model.request;

import com.t3t.bookstoreapi.payment.constant.PaymentProviderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 결제 정보 생성 요청 객체
 *
 * @author woody35545(구건모)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCreationRequest {

    /**
     * 대상 주문 및 금액 정보
     */
    @NotNull
    private Long orderId; // 결제 대상 주문 식별자
    @NotNull
    private BigDecimal totalAmount; // 총 결제 금액

    /**
     * 결제 서비스 제공자 정보
     */
    @NotNull
    private PaymentProviderType providerType; // 결제 서비스 제공자
    @NotBlank
    private String providerPaymentKey; // 결제 서비스 제공자의 결제 식별자
    @NotBlank
    private String providerOrderId; // 결제 서비스 제공자의 주문 식별자
    @NotBlank
    private String providerPaymentStatus; // 결제 서비스 제공자의 결제 상태
    @NotBlank
    private String providerPaymentReceiptUrl; // 결제 서비스 제공자의 결제 영수증 URL
    @NotNull
    private LocalDateTime providerPaymentRequestedAt; // 결제 서비스 제공자의 결제 요청 시각
    @NotNull
    private LocalDateTime providerPaymentApprovedAt; // 결제 서비스 제공자의 결제 승인 시각
}
