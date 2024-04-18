package com.t3t.bookstoreapi.payment.exception;

/**
 * Toss 결제 API 요청이 실패했을 때 발생하는 예외
 * @author woody35545(구건모)
 */
public class TossPaymentApiRequestFailedException extends RuntimeException {
    public TossPaymentApiRequestFailedException(String message) {
        super(message);
    }
}
