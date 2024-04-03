package com.t3t.bookstoreapi.payment.service;


import com.t3t.bookstoreapi.order.model.entity.Order;
import com.t3t.bookstoreapi.order.repository.OrderRepository;
import com.t3t.bookstoreapi.payment.model.entity.PaymentProvider;
import com.t3t.bookstoreapi.payment.model.entity.Payments;
import com.t3t.bookstoreapi.payment.repository.PaymentProviderRepository;
import com.t3t.bookstoreapi.payment.repository.PaymentRepository;
import com.t3t.bookstoreapi.payment.model.request.PaymentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@Transactional
public class PaymentService {

    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentProviderRepository paymentProviderRepository;

    @Autowired
    public PaymentService(OrderRepository orderRepository, PaymentRepository paymentRepository,
                          PaymentProviderRepository paymentProviderRepository) {
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
        this.paymentProviderRepository = paymentProviderRepository;
    }

    public void PaymentRequest(PaymentRequest paymentRequest) {
        Order order = orderRepository.findById(paymentRequest.getOrderId())
                .orElseThrow(() -> new IllegalArgumentException("주문 정보를 찾을 수 없습니다."));

        PaymentProvider paymentProvider;
        if (String.valueOf(order.getId()).startsWith("1")) {
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
