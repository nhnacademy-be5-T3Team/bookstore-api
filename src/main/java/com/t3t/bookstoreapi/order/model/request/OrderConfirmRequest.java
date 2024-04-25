package com.t3t.bookstoreapi.order.model.request;

import com.t3t.bookstoreapi.payment.constant.PaymentProviderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 주문 승인 요청 객체
 *
 * @Author woody35545(구건모)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderConfirmRequest {
    @NotNull(message = "주문 식별자가 누락되었습니다.")
    private Long orderId; // 주문 식별자
    @NotNull(message = "결제 제공처가 누락되었습니다.")
    private PaymentProviderType paymentProviderType; // 결제 제공처
    @NotBlank(message = "결제 제공처의 결제 키가 누락되었습니다.")
    private String paymentKey; // 결제 제공처의 결제 키
    @NotBlank(message = "결제 제공처의 주문 식별자가 누락되었습니다.")
    private String paymentOrderId; // 결제 제공처의 주문 식별자
    @NotBlank(message = "결제 금액 정보가 누락되었습니다.")
    private BigDecimal amount; // 결제 금액
}
