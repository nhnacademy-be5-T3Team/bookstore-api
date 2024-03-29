package com.t3t.bookstoreapi.payment.controller;

import com.t3t.bookstoreapi.payment.responce.TossPaymentResponse;
import com.t3t.bookstoreapi.payment.service.TossPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TossPaymentController {

    @Autowired
    private TossPaymentService tossPaymentService;
    @PostMapping("/success")
    public ResponseEntity<?> createTossPayment(@RequestBody TossPaymentResponse tossPaymentResponse) {
        System.out.println(tossPaymentResponse.getPaymentKey());

        return new ResponseEntity<>("Toss payment saved successfully", HttpStatus.OK);
    }
}
