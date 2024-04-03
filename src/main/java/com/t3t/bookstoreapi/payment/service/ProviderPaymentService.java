package com.t3t.bookstoreapi.payment.service;

import com.t3t.bookstoreapi.payment.model.response.TossPaymentResponse;

public interface ProviderPaymentService {
    void saveTossPayment(TossPaymentResponse tossPaymentResponse);
}
