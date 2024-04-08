package com.t3t.bookstoreapi.payment_test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.t3t.bookstoreapi.payment.model.entity.TossPayments;
import com.t3t.bookstoreapi.payment.model.response.TossPaymentResponse;
import com.t3t.bookstoreapi.payment.repository.TossPaymentRepository;
import com.t3t.bookstoreapi.payment.service.TossPaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

import static com.jayway.jsonpath.internal.path.PathCompiler.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class TossPaymentTest {
    @Mock
    private TossPaymentRepository tossPaymentRepository;

    @InjectMocks
    private TossPaymentService tossPaymentService;
    @Mock
    private TossPayments tossPayments;
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
            fail("Failed to parse JSON: " + e.getMessage());
        }

        // When
        tossPaymentService.saveTossPayment(tossPaymentResponse);

        // Then
        verify(tossPaymentRepository, times(1)).save(any(TossPayments.class));
    }
}


