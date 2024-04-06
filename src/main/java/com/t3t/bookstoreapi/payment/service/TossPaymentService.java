package com.t3t.bookstoreapi.payment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.t3t.bookstoreapi.payment.model.entity.TossPayments;
import com.t3t.bookstoreapi.payment.model.response.TossPaymentResponse;
import com.t3t.bookstoreapi.payment.repository.TossPaymentRepository;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.json.simple.parser.JSONParser;
import org.json.simple.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import java.util.Base64;
import java.util.Optional;


@Service
@Transactional
@Qualifier("tossPaymentService")
public class TossPaymentService implements ProviderPaymentService {


    private final TossPaymentRepository tossPaymentRepository;

    @Autowired
    public TossPaymentService(TossPaymentRepository tossPaymentRepository) {
        this.tossPaymentRepository = tossPaymentRepository;
    }


    public void saveTossPayment(TossPaymentResponse tossPaymentResponse) {
        TossPayments tossPayment = mapToTossPaymentEntity(tossPaymentResponse);
        tossPaymentRepository.save(tossPayment);
    }

    private TossPayments mapToTossPaymentEntity(TossPaymentResponse tossPaymentResponse) {
        TossPayments tossPayment = new TossPayments();
        tossPayment.setTossPaymentKey(tossPaymentResponse.getPaymentKey());
        tossPayment.setTossOrderId(tossPaymentResponse.getOrderId());
        tossPayment.setTossPaymentStatus(tossPaymentResponse.getStatus());
        tossPayment.setTossPaymentReceiptUrl(tossPaymentResponse.getReceipt().getUrl());

        return tossPayment;
    }

    @Transactional(readOnly = true)
    public TossPayments getTossPaymentsByPaymentId(Long paymentId) {

        Optional<TossPayments> tossPaymentsOptional = tossPaymentRepository.findByTossPaymentIdPayment(paymentId);
        if (tossPaymentsOptional.isPresent()) {
            TossPayments tossPayments = tossPaymentsOptional.get();
            return tossPayments;
        } else {
            return null;
        }

    }

    public void updateTossPayment(TossPaymentResponse tossPaymentResponse) {
        Optional<TossPayments> existingPaymentOptional = tossPaymentRepository.findByTossPaymentKey(tossPaymentResponse.getPaymentKey());
        if (existingPaymentOptional.isPresent()) {
            TossPayments existingPayment = existingPaymentOptional.get();
            existingPayment.setTossOrderId(tossPaymentResponse.getOrderId());
            existingPayment.setTossPaymentStatus(tossPaymentResponse.getStatus());
            existingPayment.setTossPaymentReceiptUrl(tossPaymentResponse.getReceipt().getUrl());
            tossPaymentRepository.save(existingPayment);
        }
    }
}




