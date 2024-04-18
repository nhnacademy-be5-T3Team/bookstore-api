package com.t3t.bookstoreapi.payment.service;

import com.t3t.bookstoreapi.payment.model.entity.Payment;
import com.t3t.bookstoreapi.payment.model.request.PaymentConfirmRequest;

/**
 * 결제 제공자 서비스 인터페이스
 * @author woody35545(구건모)
 */
public interface PaymentProviderService {
    /**
     * Toss paymentKey 에 해당하는 결제를 검증하고 승인한다.<br>
     * 결제 인증이 유효한 10분 안에 결제 승인 API를 호출하지 않으면 해당 결제는 만료된다.<br>
     *
     * @param paymentRequestBody 결제 요청 정보
     *
     * @author woody35545(구건모)
     */
    Payment confirmPayment(PaymentConfirmRequest paymentRequestBody);
}
