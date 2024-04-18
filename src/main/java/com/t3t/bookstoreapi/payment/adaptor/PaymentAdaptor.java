package com.t3t.bookstoreapi.payment.adaptor;

import com.t3t.bookstoreapi.payment.model.entity.Payment;
import com.t3t.bookstoreapi.payment.model.request.PaymentConfirmRequest;
import org.json.simple.parser.ParseException;

/**
 * 여러 결제 제공자에서 제공하는 API 를 호출하기 위한 어댑터 인터페이스
 * @author woody35545(구건모)
 */
public interface PaymentAdaptor {

    /**
     * 결제 승인 API 를 호출하여 결제 제공자측에서 결제가 처리되도록 요청한다.
     * @author woody35545(구건모)
     */
    Payment confirmPayment(PaymentConfirmRequest paymentRequestBody) throws ParseException;
}
