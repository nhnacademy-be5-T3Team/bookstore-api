package com.t3t.bookstoreapi.payment.service;

import com.t3t.bookstoreapi.payment.entity.Orders;
import com.t3t.bookstoreapi.payment.entity.PaymentProvider;
import com.t3t.bookstoreapi.payment.entity.Payments;
import com.t3t.bookstoreapi.payment.repository.PaymentProviderRepository;
import com.t3t.bookstoreapi.payment.repository.PaymentRepository;
import com.t3t.bookstoreapi.payment.request.PaymentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;

import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;

@Service
public class PaymentService {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PaymentRepository paymentRepository;


    @Autowired
    private PaymentProviderRepository paymentProviderRepository;

    public void PaymentRequest(PaymentRequest paymentRequest) {
        Orders order = entityManager.find(Orders.class, paymentRequest.getOrderId());

        PaymentProvider paymentProvider;
        if (String.valueOf(order.getOrderId()).startsWith("1")) {
            paymentProvider = paymentProviderRepository.findByPaymentProviderName("토스");
        } else {
            paymentProvider = paymentProviderRepository.findByPaymentProviderName("기본");
        }

        Payments payment = new Payments();
        payment.setOrderId(order);
        payment.setPaymentProviderId(paymentProvider);
        payment.setPaymentTime(LocalDateTime.now());
        payment.setPaymentPrice(paymentRequest.getPaymentPrice());

        paymentRepository.save(payment);
    }
}
