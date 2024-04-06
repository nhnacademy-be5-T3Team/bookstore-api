package com.t3t.bookstoreapi.payment.model.response;

import com.t3t.bookstoreapi.order.model.entity.Order;
import com.t3t.bookstoreapi.payment.model.entity.PaymentProvider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {
    private Long paymentId;

    private Order orderId;

    private PaymentProvider paymentProviderId;

    private LocalDateTime paymentTime;

    private BigDecimal paymentPrice;
}
