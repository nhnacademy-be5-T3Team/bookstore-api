package com.t3t.bookstoreapi.payment.service;

import com.t3t.bookstoreapi.payment.model.entity.TossPayments;
import com.t3t.bookstoreapi.payment.model.response.TossPaymentResponse;
import com.t3t.bookstoreapi.payment.repository.TossPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
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
 /*   public  cancelPayment(PaymentCancelRequest paymentCancelRequest) {
        String paymentKey = "";
        String cancelReason = "고객 변심";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBasicAuth(SECRET_KEY, "");

        String requestBodyString = "{\"cancelReason\":\"" + cancelReason + "\"}";

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBodyString, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(
                "https://api.tosspayments.com/v1/payments/" + paymentKey + "/cancel",
                requestEntity,
                String.class);

        return responseEntity;
    }*/


    public TossPayments getTossPaymentsByPaymentId(Long paymentId) {
        return tossPaymentRepository.findByTossPaymentIdPayment(paymentId);
    }
}




