package com.t3t.bookstoreapi.payment.service;

import com.t3t.bookstoreapi.payment.model.entity.TossPayments;
import com.t3t.bookstoreapi.payment.model.response.TossPaymentResponse;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;

public interface ProviderPaymentService {
    void saveTossPayment(TossPaymentResponse tossPaymentResponse);
    TossPayments getTossPaymentsByPaymentId(Long paymentsId);
    void updateTossPayment(TossPaymentResponse tossPaymentResponse);

}
