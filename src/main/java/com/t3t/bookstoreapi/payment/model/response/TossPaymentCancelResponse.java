package com.t3t.bookstoreapi.payment.model.response;

import com.t3t.bookstoreapi.payment.model.entity.TossPayments;
import lombok.Data;

@Data
public class TossPaymentCancelResponse {
    private String tossPaymentKey;
    private String tossOrderId;
    private String tossPaymentStatus;
    private String tossPaymentReceiptUrl;

    public static TossPaymentCancelResponse fromEntity(TossPayments tossPayments) {
        TossPaymentCancelResponse dto = new TossPaymentCancelResponse();
        dto.setTossPaymentKey(tossPayments.getTossPaymentKey());
        dto.setTossOrderId(tossPayments.getTossOrderId());
        dto.setTossPaymentStatus(tossPayments.getTossPaymentStatus());
        dto.setTossPaymentReceiptUrl(tossPayments.getTossPaymentReceiptUrl());
        return dto;
    }
}



