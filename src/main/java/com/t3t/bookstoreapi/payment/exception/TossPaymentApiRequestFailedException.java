package com.t3t.bookstoreapi.payment.exception;

/**
 * Toss 결제 API 요청이 실패했을 때 발생하는 예외
 * @author woody35545(구건모)
 */
public class TossPaymentApiRequestFailedException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Toss API 호출에 실패하였습니다.";

    public TossPaymentApiRequestFailedException() {
        super(DEFAULT_MESSAGE);
    }

    public TossPaymentApiRequestFailedException(String message) {
        super(new StringBuilder().append(DEFAULT_MESSAGE).append(" - ").append(message).toString());
    }


}
