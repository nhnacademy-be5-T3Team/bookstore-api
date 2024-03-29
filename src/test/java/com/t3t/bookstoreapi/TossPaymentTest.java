package com.t3t.bookstoreapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.t3t.bookstoreapi.payment.entity.TossPayments;
import com.t3t.bookstoreapi.payment.repository.TossPaymentRepository;
import com.t3t.bookstoreapi.payment.responce.TossPaymentResponse;
import com.t3t.bookstoreapi.payment.service.TossPaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class TossPaymentTest {

    @Test
    void contextLoads() {
    }
    @Test
    public void testTossPaymentResponseMapping() {
        String json = "{\"paymentKey\":\"12345\",\"orderId\":\"67890\",\"status\":\"success\",\"receipt\":{\"url\":\"http://example.com/receipt\"}}";
        TossPaymentResponse tossPaymentResponse = new TossPaymentResponse();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            tossPaymentResponse = objectMapper.readValue(json, TossPaymentResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals("12345", tossPaymentResponse.getPaymentKey());
        assertEquals("67890", tossPaymentResponse.getOrderId());
        assertEquals("success", tossPaymentResponse.getStatus());
        assertEquals("http://example.com/receipt", tossPaymentResponse.getReceipt().getUrl());
        System.out.println(tossPaymentResponse.getPaymentKey());

    }

    @Mock
    private TossPaymentRepository tossPaymentRepository;

    @InjectMocks
    private TossPaymentService tossPaymentService;
    @Mock
    private TossPayments tossPayments;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    @DisplayName("toss_payment 객체 생성 test")
    public void testCreateTossPayment() {
        // Given
        String json = "{\"paymentKey\":\"12345\",\"orderId\":\"67890\",\"status\":\"success\",\"receipt\":{\"url\":\"http://example.com/receipt\"}}";
        TossPaymentResponse tossPaymentResponse = null;
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            tossPaymentResponse = objectMapper.readValue(json, TossPaymentResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // When
        tossPaymentService.saveTossPayment(tossPaymentResponse);

        System.out.println(tossPaymentResponse.getPaymentKey());
        System.out.println(tossPayments.getTossPaymentKey());
        // Then

        verify(tossPaymentRepository, times(1)).save(any(TossPayments.class));
    }
}


