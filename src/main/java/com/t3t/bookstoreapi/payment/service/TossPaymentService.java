package com.t3t.bookstoreapi.payment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.t3t.bookstoreapi.payment.model.entity.TossPayments;
import com.t3t.bookstoreapi.payment.model.response.TossPaymentResponse;
import com.t3t.bookstoreapi.payment.repository.TossPaymentRepository;
import com.t3t.bookstoreapi.payment.service.TossPaymentService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;



@Service
@Qualifier("tossPaymentService")
public class TossPaymentService implements ProviderPaymentService{


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

}




