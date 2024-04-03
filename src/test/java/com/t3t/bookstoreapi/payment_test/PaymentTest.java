package com.t3t.bookstoreapi.payment_test;


import com.t3t.bookstoreapi.order.model.entity.Order;
import com.t3t.bookstoreapi.payment.model.entity.PaymentProvider;
import com.t3t.bookstoreapi.payment.model.entity.Payments;
import com.t3t.bookstoreapi.payment.repository.PaymentProviderRepository;
import com.t3t.bookstoreapi.payment.repository.PaymentRepository;
import com.t3t.bookstoreapi.payment.model.request.PaymentRequest;
import com.t3t.bookstoreapi.payment.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import java.math.BigDecimal;

@SpringBootTest
public class PaymentTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private PaymentProviderRepository paymentProviderRepository;

    @InjectMocks
    private PaymentService paymentService;
    @Mock
    private Payments payments;

    @BeforeEach
    public void setUp() {
    }

    @Test
    @DisplayName("payment 객체 생성 test")
    public void testCreatePayment() {
        Order order = new Order();
        order.setId(1L);


        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setOrderId(order.getId());
        paymentRequest.setPaymentPrice(BigDecimal.valueOf(10000));


        PaymentProvider paymentProvider = new PaymentProvider();
        paymentProvider.setPaymentProviderName("토스");

        Mockito.when(entityManager.find(Order.class, 1L)).thenReturn(order);
        Mockito.when(paymentProviderRepository.findByPaymentProviderName("토스")).thenReturn(paymentProvider);

        paymentService.PaymentRequest(paymentRequest);
        Mockito.verify(paymentRepository, Mockito.times(1)).save(Mockito.any(Payments.class));
    }
}
