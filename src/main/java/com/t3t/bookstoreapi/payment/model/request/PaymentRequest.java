package com.t3t.bookstoreapi.payment.model.request;

import lombok.*;

import java.math.BigDecimal;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PaymentRequest {
    private Long orderId;
    private BigDecimal paymentPrice;
}
