package com.t3t.bookstoreapi.payment.exception;

/**
 * 지원하지 않는 결제 서비스 제공자 타입으로 결제를 시도할 때 발생하는 예외
 *
 * @author woody35545(구건모)
 */
public class UnsupportedPaymentProviderTypeException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "지원하지 않는 결제 서비스입니다.";

    public UnsupportedPaymentProviderTypeException() {
        super(DEFAULT_MESSAGE);
    }

    public UnsupportedPaymentProviderTypeException(String forTarget) {
        super(new StringBuilder().append(DEFAULT_MESSAGE).append(" - ").append(forTarget).toString());
    }
}
