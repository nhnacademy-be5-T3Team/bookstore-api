package com.t3t.bookstoreapi.payment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.t3t.bookstoreapi.order.model.entity.Order;
import com.t3t.bookstoreapi.order.repository.OrderRepository;
import com.t3t.bookstoreapi.payment.model.entity.Payments;
import com.t3t.bookstoreapi.payment.model.entity.TossPayments;
import com.t3t.bookstoreapi.payment.model.request.PaymentCancelRequest;
import com.t3t.bookstoreapi.payment.model.response.TossPaymentCancelResponse;
import com.t3t.bookstoreapi.payment.model.response.TossPaymentResponse;
import com.t3t.bookstoreapi.payment.repository.PaymentProviderRepository;
import com.t3t.bookstoreapi.payment.repository.PaymentRepository;
import com.t3t.bookstoreapi.payment.service.PaymentService;
import com.t3t.bookstoreapi.payment.service.ProviderPaymentService;
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
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RestController
public class TossPaymentController {

    private final ProviderPaymentService providerPaymentService;

    private final PaymentService paymentService;

    @Autowired
    public TossPaymentController(PaymentService paymentService, @Qualifier("tossPaymentService")ProviderPaymentService providerPaymentService) {
        this.paymentService = paymentService;
        this.providerPaymentService = providerPaymentService;
    }
    @PostMapping(value = "/confirm")
    public ResponseEntity<?> confirmPayment(@RequestBody String jsonBody) throws Exception {

        JSONParser parser = new JSONParser();
        String orderId;
        String amount;
        String paymentKey;
        try {
            JSONObject requestData = (JSONObject) parser.parse(jsonBody);
            paymentKey = (String) requestData.get("paymentKey");
            orderId = (String) requestData.get("orderId");
            amount = (String) requestData.get("amount");

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        JSONObject obj = new JSONObject();
        obj.put("orderId", orderId);
        obj.put("amount", amount);
        obj.put("paymentKey", paymentKey);

        String widgetSecretKey = "test_sk_Z1aOwX7K8m1OPOjdk5qW8yQxzvNP:";

        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encodedBytes = encoder.encode((widgetSecretKey + ":").getBytes(StandardCharsets.UTF_8));
        String authorizations = "Basic " + new String(encodedBytes);

        URL url = new URL("https://api.tosspayments.com/v1/payments/confirm");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Authorization", authorizations);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);


        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(obj.toString().getBytes("UTF-8"));

        int code = connection.getResponseCode();
        boolean isSuccess = code == 200;

        InputStream responseStream = isSuccess ? connection.getInputStream() : connection.getErrorStream();

        Reader reader = new InputStreamReader(responseStream, StandardCharsets.UTF_8);

        if (isSuccess) {
            ObjectMapper objectMapper = new ObjectMapper();
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            TossPaymentResponse paymentResponse = objectMapper.readValue(reader, TossPaymentResponse.class);
            responseStream.close();
            providerPaymentService.saveTossPayment(paymentResponse);
            return ResponseEntity.status(code).body(jsonObject);
        } else {
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            responseStream.close();
            return ResponseEntity.status(code).body(jsonObject);
        }
    }


    @GetMapping("/{orderId}")
    public ResponseEntity<TossPaymentCancelResponse> getPaymentAndTossInfo(@RequestParam("orderId") Long orderId) {
        Payments payment = paymentService.findPaymentByOrderId(orderId);
        TossPayments tossPayments = providerPaymentService.getTossPaymentsByPaymentId(payment.getPaymentId());
        TossPaymentCancelResponse tossPaymentCancelResponse = TossPaymentCancelResponse.fromEntity(tossPayments);
        return ResponseEntity.ok(tossPaymentCancelResponse);
    }
}
