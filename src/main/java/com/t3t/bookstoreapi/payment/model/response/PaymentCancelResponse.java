package com.t3t.bookstoreapi.payment.model.response;

import com.t3t.bookstoreapi.payment.model.entity.TossPayments;
import lombok.Data;

@Data
public class PaymentCancelResponse {
    private String tossPaymentKey;
    private String tossOrderId;
    private String tossPaymentStatus;
    private String tossPaymentReceiptUrl;

    public static PaymentCancelResponse fromEntity(TossPayments tossPayments) {
        PaymentCancelResponse dto = new PaymentCancelResponse();
        dto.setTossPaymentKey(tossPayments.getTossPaymentKey());
        dto.setTossOrderId(tossPayments.getTossOrderId());
        dto.setTossPaymentStatus(tossPayments.getTossPaymentStatus());
        dto.setTossPaymentReceiptUrl(tossPayments.getTossPaymentReceiptUrl());
        return dto;
    }
}



