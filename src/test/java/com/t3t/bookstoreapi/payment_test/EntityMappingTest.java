package com.t3t.bookstoreapi.payment_test;


import com.t3t.bookstoreapi.order.model.entity.Order;
import com.t3t.bookstoreapi.order.repository.OrderRepository;
import com.t3t.bookstoreapi.payment.model.entity.PaymentProvider;
import com.t3t.bookstoreapi.payment.model.entity.Payments;
import com.t3t.bookstoreapi.payment.model.entity.TossPayments;
import com.t3t.bookstoreapi.payment.repository.PaymentProviderRepository;

import com.t3t.bookstoreapi.payment.repository.PaymentRepository;
import com.t3t.bookstoreapi.payment.repository.TossPaymentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class EntityMappingTest {

    @Autowired
    private OrderRepository ordersRepository;

    @Autowired
    private PaymentProviderRepository paymentProviderRepository;

    @Autowired
    private PaymentRepository paymentsRepository;

    @Autowired
    private TossPaymentRepository tossPaymentsRepository;

    @Test
    @DisplayName("Entity Mapping test")
    public void testEntitiesMapping() {
        // Create a payment provider
        PaymentProvider paymentProvider = new PaymentProvider();
        paymentProvider.setPaymentProviderId(1);
        paymentProvider.setPaymentProviderName("Test Payment Provider");
        paymentProviderRepository.save(paymentProvider);

        // Create an order
        Order order = new Order();
        order.setId(1L);
        ordersRepository.save(order);

        // Create a payment
        Payments payment = new Payments();
        payment.setOrderId(order);
        payment.setPaymentProviderId(paymentProvider);
        LocalDateTime specificTime = LocalDateTime.of(2024, 3, 29, 14, 50, 52);
        payment.setPaymentTime(specificTime);
        payment.setPaymentPrice(BigDecimal.valueOf(100));
        paymentsRepository.save(payment);


        // Verify payment persistence
        Payments savedPayment = paymentsRepository.findById(payment.getPaymentId()).orElse(null);
        assertNotNull(savedPayment);
        assertEquals(order.getId(), savedPayment.getOrderId().getId());
        assertEquals(paymentProvider.getPaymentProviderId(), savedPayment.getPaymentProviderId().getPaymentProviderId());
        assertEquals(payment.getPaymentTime(), savedPayment.getPaymentTime());
        assertEquals(payment.getPaymentPrice(), BigDecimal.valueOf(100));

        // Create a Toss payment
        TossPayments.TossPaymentId tossPaymentId = new TossPayments.TossPaymentId();
        tossPaymentId.setPayment(payment);
        TossPayments tossPayment = TossPayments.builder()
                .tossPaymentId(tossPaymentId)
                .tossOrderId("toss_order_id")
                .tossPaymentKey("toss_payment_key")
                .tossPaymentStatus("SUCCESS")
                .tossPaymentReceiptUrl("http://example.com")
                .build();
        tossPaymentsRepository.save(tossPayment);

        TossPayments savedTossPayment = tossPaymentsRepository.findById(tossPayment.getTossPaymentId()).orElse(null);
        assertNotNull(savedTossPayment);
        assertEquals("toss_order_id", savedTossPayment.getTossOrderId());
        assertEquals("toss_payment_key", savedTossPayment.getTossPaymentKey());
        assertEquals("SUCCESS", savedTossPayment.getTossPaymentStatus());
        assertEquals("http://example.com", savedTossPayment.getTossPaymentReceiptUrl());
    }
}
