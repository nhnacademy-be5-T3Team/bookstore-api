package com.t3t.bookstoreapi.payment_test;

import com.t3t.bookstoreapi.member.domain.Member;
import com.t3t.bookstoreapi.order.model.entity.Delivery;
import com.t3t.bookstoreapi.order.model.entity.Order;
import com.t3t.bookstoreapi.payment.controller.PaymentController;
import com.t3t.bookstoreapi.payment.controller.TossPaymentController;
import com.t3t.bookstoreapi.payment.model.entity.Payments;
import com.t3t.bookstoreapi.payment.model.entity.TossPayments;
import com.t3t.bookstoreapi.payment.model.request.PaymentCancelRequest;
import com.t3t.bookstoreapi.payment.model.request.PaymentRequest;
import com.t3t.bookstoreapi.payment.model.response.TossPaymentCancelResponse;
import com.t3t.bookstoreapi.payment.service.PaymentService;
import com.t3t.bookstoreapi.payment.service.ProviderPaymentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TossPaymentControllerTest {

    @Mock
    private PaymentService paymentService;

    @Mock
    private ProviderPaymentService providerPaymentService;

    @InjectMocks
    private TossPaymentController tossPaymentController;

    @Test
    public void testGetPaymentAndTossInfo() {
        Long orderId = 1L;

        PaymentService paymentService = mock(PaymentService.class);
        ProviderPaymentService providerPaymentService = mock(ProviderPaymentService.class);

        Payments payment = new Payments();
        payment.setPaymentId(1L);

        TossPayments tossPayments = new TossPayments();
        tossPayments.setTossPaymentKey("test_key");
        tossPayments.setTossOrderId("test_order_id");
        tossPayments.setTossPaymentStatus("success");
        tossPayments.setTossPaymentReceiptUrl("https://test-url.com");

        when(paymentService.findPaymentByOrderId(orderId)).thenReturn(payment);
        when(providerPaymentService.getTossPaymentsByPaymentId(payment.getPaymentId())).thenReturn(tossPayments);
        TossPaymentController tossPaymentController = new TossPaymentController(paymentService, providerPaymentService);
        ResponseEntity<TossPaymentCancelResponse> responseEntity = tossPaymentController.getPaymentAndTossInfo(orderId);

        assertNotNull(responseEntity);

        assertEquals(200, responseEntity.getStatusCodeValue());
        TossPaymentCancelResponse response = responseEntity.getBody();
        assertEquals(tossPayments.getTossPaymentKey(), response.getTossPaymentKey());
        assertEquals(tossPayments.getTossOrderId(), response.getTossOrderId());
        assertEquals(tossPayments.getTossPaymentStatus(), response.getTossPaymentStatus());
        assertEquals(tossPayments.getTossPaymentReceiptUrl(), response.getTossPaymentReceiptUrl());
    }
    }

