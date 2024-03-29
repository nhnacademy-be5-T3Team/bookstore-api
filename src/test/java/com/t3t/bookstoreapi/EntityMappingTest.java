package com.t3t.bookstoreapi;

import com.t3t.bookstoreapi.payment.entity.Orders;
import com.t3t.bookstoreapi.payment.entity.PaymentProvider;
import com.t3t.bookstoreapi.payment.entity.Payments;
import com.t3t.bookstoreapi.payment.entity.TossPayments;
import com.t3t.bookstoreapi.payment.repository.PaymentProviderRepository;

import com.t3t.bookstoreapi.payment.repository.PaymentRepository;
import com.t3t.bookstoreapi.payment.repository.TossPaymentRepository;
import com.t3t.bookstoreapi.payment.repository.orderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class EntityMappingTest {

    @Autowired
    private orderRepository ordersRepository;

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
        Orders order = new Orders();
        order.setOrderId(1L);
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
        assertEquals(order.getOrderId(), savedPayment.getOrderId().getOrderId());
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
