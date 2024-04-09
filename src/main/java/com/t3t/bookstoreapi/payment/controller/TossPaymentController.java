package com.t3t.bookstoreapi.payment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.t3t.bookstoreapi.model.response.BaseResponse;
import com.t3t.bookstoreapi.payment.model.entity.Payments;
import com.t3t.bookstoreapi.payment.model.entity.TossPayments;
import com.t3t.bookstoreapi.payment.model.request.PaymentCancelRequest;
import com.t3t.bookstoreapi.payment.model.response.PaymentCancelResponse;
import com.t3t.bookstoreapi.payment.model.response.PaymentResponse;
import com.t3t.bookstoreapi.payment.model.response.TossPaymentResponse;
import com.t3t.bookstoreapi.payment.service.PaymentService;
import com.t3t.bookstoreapi.payment.service.ProviderPaymentService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.math.BigDecimal;
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
    String widgetSecretKey = "test_sk_Z1aOwX7K8m1OPOjdk5qW8yQxzvNP:";
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
    @GetMapping("payments/{orderId}")
    public ResponseEntity<BaseResponse<PaymentCancelResponse>> getPaymentAndTossInfo(@RequestParam("orderId") Long orderId) {
        Payments payment = paymentService.findPaymentByOrderId(orderId);
        return ResponseEntity.ok(new BaseResponse<PaymentCancelResponse>().data(PaymentCancelResponse
                .fromEntity(providerPaymentService.getTossPaymentsByPaymentId(payment.getPaymentId()))));
    }

    @PostMapping("/cancel")
        public ResponseEntity<?> cancelPayment(PaymentCancelRequest request, PaymentCancelResponse response) {
        BigDecimal paymentPrice = request.getPaymentPrice();
        String cancelReason = request.getCancelReason();
        String paymentKey = response.getTossPaymentKey();

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(widgetSecretKey);

        String url = "https://api.tosspayments.com/v1/payments/" + paymentKey + "/cancel";
        HttpEntity<String> requestEntity = new HttpEntity<>("{\"cancelReason\": \"" + cancelReason + "\"}", headers);

        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                ObjectMapper objectMapper = new ObjectMapper();
                TossPaymentResponse tossPaymentResponse = objectMapper.readValue(responseEntity.getBody(), TossPaymentResponse.class);
                providerPaymentService.updateTossPayment(tossPaymentResponse);
            }
            return ResponseEntity.ok(responseEntity.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}

