package com.t3t.bookstoreapi;

import com.t3t.bookstoreapi.payment.entity.Orders;
import com.t3t.bookstoreapi.payment.entity.PaymentProvider;
import com.t3t.bookstoreapi.payment.entity.Payments;
import com.t3t.bookstoreapi.payment.repository.PaymentProviderRepository;
import com.t3t.bookstoreapi.payment.repository.PaymentRepository;
import com.t3t.bookstoreapi.payment.request.PaymentRequest;
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
        Orders order = new Orders();
        order.setOrderId(1L);


        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setOrderId(order.getOrderId());
        paymentRequest.setPaymentPrice(BigDecimal.valueOf(10000));


        PaymentProvider paymentProvider = new PaymentProvider();
        paymentProvider.setPaymentProviderName("토스");

        Mockito.when(entityManager.find(Orders.class, 1L)).thenReturn(order);
        Mockito.when(paymentProviderRepository.findByPaymentProviderName("토스")).thenReturn(paymentProvider);

        // when
        paymentService.PaymentRequest(paymentRequest);
        // then
        Mockito.verify(paymentRepository, Mockito.times(1)).save(Mockito.any(Payments.class));
    }
}
