package com.t3t.bookstoreapi.payment_test;

import com.t3t.bookstoreapi.model.response.BaseResponse;
import com.t3t.bookstoreapi.payment.controller.TossPaymentController;
import com.t3t.bookstoreapi.payment.model.entity.Payments;
import com.t3t.bookstoreapi.payment.model.entity.TossPayments;
import com.t3t.bookstoreapi.payment.model.response.PaymentCancelResponse;
import com.t3t.bookstoreapi.payment.service.PaymentService;
import com.t3t.bookstoreapi.payment.service.ProviderPaymentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
        ResponseEntity<BaseResponse<PaymentCancelResponse>> responseEntity = tossPaymentController.getPaymentAndTossInfo(orderId);

        assertNotNull(responseEntity);

        assertEquals(200, responseEntity.getStatusCodeValue());
        BaseResponse<PaymentCancelResponse> response = responseEntity.getBody();
        assert response != null;
        PaymentCancelResponse responseData = response.getData();
        assertEquals(tossPayments.getTossPaymentKey(), responseData.getTossPaymentKey());
        assertEquals(tossPayments.getTossOrderId(), responseData.getTossOrderId());
        assertEquals(tossPayments.getTossPaymentStatus(), responseData.getTossPaymentStatus());
        assertEquals(tossPayments.getTossPaymentReceiptUrl(), responseData.getTossPaymentReceiptUrl());
    }

    }

