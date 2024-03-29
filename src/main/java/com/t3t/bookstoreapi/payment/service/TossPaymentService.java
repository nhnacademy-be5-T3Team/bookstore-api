package com.t3t.bookstoreapi.payment.service;

import com.t3t.bookstoreapi.payment.entity.PaymentProvider;
import com.t3t.bookstoreapi.payment.entity.TossPayments;
import com.t3t.bookstoreapi.payment.repository.TossPaymentRepository;
import com.t3t.bookstoreapi.payment.responce.TossPaymentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TossPaymentService {

    @Autowired
    private TossPaymentRepository tossPaymentRepository;

    public void saveTossPayment(TossPaymentResponse tossPaymentResponse) {
        TossPayments tossPayment = mapToTossPaymentEntity(tossPaymentResponse);
        tossPaymentRepository.save(tossPayment);
        System.out.println("Toss payment saved successfully: " + tossPayment);
    }

    private TossPayments mapToTossPaymentEntity(TossPaymentResponse tossPaymentResponse) {
        TossPayments tossPayment = new TossPayments();
        tossPayment.setTossPaymentKey(tossPaymentResponse.getPaymentKey());
        tossPayment.setTossOrderId(tossPaymentResponse.getOrderId());
        tossPayment.setTossPaymentStatus(tossPaymentResponse.getStatus());
        tossPayment.setTossPaymentReceiptUrl(tossPaymentResponse.getReceipt().getUrl());

        return tossPayment;
    }
}
