package com.t3t.bookstoreapi.payment.adaptor;

import com.t3t.bookstoreapi.payment.exception.TossPaymentApiRequestFailedException;
import com.t3t.bookstoreapi.payment.model.entity.TossPayment;
import com.t3t.bookstoreapi.payment.model.request.PaymentConfirmRequest;
import com.t3t.bookstoreapi.property.TossPaymentProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

/**
 * Toss 결제 서비스 API 를 호출하고 응답을 처리하는 어댑터 클래스
 * @author woody35545(구건모)
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TossPaymentAdaptor implements PaymentAdaptor {

    private final RestTemplate restTemplate;
    private final TossPaymentProperties tossPaymentProperties;
    private static final String TOSS_AUTHORIZE_HEADER_PREFIX = "Basic";

    /**
     * {@inheritDoc}
     * @see <a href="https://docs.tosspayments.com/reference#%EA%B2%B0%EC%A0%9C-%EC%8A%B9%EC%9D%B8">toss 결제 승인 api reference</a>
     * @author woody35545(구건모)
     */
    @Override
    public TossPayment confirmPayment(PaymentConfirmRequest paymentConfirmRequest) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.AUTHORIZATION, TOSS_AUTHORIZE_HEADER_PREFIX + " "
                + Base64.getEncoder().encodeToString((tossPaymentProperties.getWidgetSecretKey() + ":").getBytes()));

        HttpEntity<PaymentConfirmRequest> requestEntity = new HttpEntity<>(paymentConfirmRequest, headers);

        try {
            ResponseEntity<TossPayment> responseEntity = restTemplate.exchange(
                    "https://api.tosspayments.com/v1/payments/confirm",
                    HttpMethod.POST, requestEntity, TossPayment.class);

            HttpStatus statusCode = responseEntity.getStatusCode();
            TossPayment tossPayment = responseEntity.getBody();

            if (statusCode != HttpStatus.OK) {
                throw new TossPaymentApiRequestFailedException("statusCode: " + statusCode.value());
            }

            if (tossPayment == null) {
                throw new TossPaymentApiRequestFailedException("응답 데이터가 없습니다.");
            }

            return tossPayment;

        } catch(HttpClientErrorException httpClientErrorException){
            try {
                throw new TossPaymentApiRequestFailedException(
                        ((JSONObject) new JSONParser().parse(httpClientErrorException.getResponseBodyAsString())).get("message").toString());
            } catch (ParseException parseException) {
                throw new RuntimeException(parseException);
            }
        }
    }
}
