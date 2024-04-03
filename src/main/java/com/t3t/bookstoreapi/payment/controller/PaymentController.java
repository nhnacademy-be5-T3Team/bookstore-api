package com.t3t.bookstoreapi.payment.controller;

import com.t3t.bookstoreapi.payment.model.entity.Payments;
import com.t3t.bookstoreapi.payment.model.entity.TossPayments;
import com.t3t.bookstoreapi.payment.model.request.PaymentRequest;
import com.t3t.bookstoreapi.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
    @PostMapping("/Payment")
    public ResponseEntity<String> processPayment(@RequestBody PaymentRequest paymentRequest) {
        paymentService.PaymentRequest(paymentRequest);
        return new ResponseEntity<>("Payment processed successfully", HttpStatus.OK);
    }
}
