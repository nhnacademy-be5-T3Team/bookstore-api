package com.t3t.bookstoreapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.t3t.bookstoreapi.payment.controller.TossPaymentController;
import com.t3t.bookstoreapi.payment.entity.TossPayments;
import com.t3t.bookstoreapi.payment.repository.TossPaymentRepository;
import com.t3t.bookstoreapi.payment.responce.TossPaymentResponse;
import com.t3t.bookstoreapi.payment.service.TossPaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class BookstoreApiApplicationTests {

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
    @InjectMocks
    private TossPaymentController tossPaymentController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
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
        System.out.println(tossPaymentResponse);
        // Then
        // Verify that the save method of tossPaymentRepository is called with the correct argument
        verify(tossPaymentRepository, times(1)).save(any(TossPayments.class));
    }

    private TossPayments mapToTossPaymentEntity(TossPaymentResponse tossPaymentResponse) {
        TossPayments tossPayment = new TossPayments();
        tossPayment.setTossPaymentKey(tossPaymentResponse.getPaymentKey());
        tossPayment.setTossOrderId(tossPaymentResponse.getOrderId());
        tossPayment.setTossPaymentStatus(tossPaymentResponse.getStatus());
        tossPayment.setTossPaymentReceiptUrl(tossPaymentResponse.getReceipt().getUrl());
        System.out.println(tossPayment);
        return tossPayment;
    }
}


