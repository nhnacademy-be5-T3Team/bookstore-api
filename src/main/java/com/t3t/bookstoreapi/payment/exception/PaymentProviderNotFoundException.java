package com.t3t.bookstoreapi.payment.exception;

/**
 * 결제 서비스 제공자를 찾을 수 없을 때 발생하는 예외
 * @author woody35545(구건모)
 */
public class PaymentProviderNotFoundException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "결제 서비스 제공자를 찾을 수 없습니다.";

    public PaymentProviderNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public PaymentProviderNotFoundException(String forTarget) {
        super(new StringBuilder().append(DEFAULT_MESSAGE).append(" (").append(forTarget).append(")").toString());
    }
}
