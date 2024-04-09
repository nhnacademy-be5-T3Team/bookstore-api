package com.t3t.bookstoreapi.payment.controller;

import com.t3t.bookstoreapi.model.response.BaseResponse;
import com.t3t.bookstoreapi.payment.model.entity.Payments;
import com.t3t.bookstoreapi.payment.model.entity.TossPayments;
import com.t3t.bookstoreapi.payment.model.request.PaymentRequest;
import com.t3t.bookstoreapi.payment.model.response.PaymentResponse;
import com.t3t.bookstoreapi.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
    @PostMapping("/Payment")
    public ResponseEntity<BaseResponse<PaymentResponse>> processPayment(@RequestBody PaymentRequest paymentRequest) {
        paymentService.PaymentRequest(paymentRequest);
        return ResponseEntity.ok(new BaseResponse<PaymentResponse>().data(paymentService.PaymentRequest(paymentRequest)));
    }
}
