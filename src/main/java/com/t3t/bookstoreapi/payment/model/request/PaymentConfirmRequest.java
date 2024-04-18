package com.t3t.bookstoreapi.payment.model.request;

import lombok.Getter;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

/**
 * 결제 제공처에서 결제 승인을 요청할 때 사용하는 요청 객체
 * @author woody35545(구건모)
 */
@Getter
public class PaymentConfirmRequest {
    /**
     * 결제 제공처를 사용하는 결제의 경우 해당 결제 제공처에서 사용하는 결제 ID를 사용한다.
     * @author woody35545(구건모)
     */
    @NotBlank
    private String orderId;
    @NotBlank
    private String paymentKey;
    @NotBlank
    private BigDecimal amount;
}
