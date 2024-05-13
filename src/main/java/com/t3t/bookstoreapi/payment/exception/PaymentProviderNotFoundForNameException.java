package com.t3t.bookstoreapi.payment.exception;

/**
 * 결제 제공자 이름에 대한 결제 제공자를 찾을 수 없을 때 발생하는 예외
 * @author woody35545(구건모)
 */
public class PaymentProviderNotFoundForNameException extends PaymentProviderNotFoundException {
    public PaymentProviderNotFoundForNameException(String name) {
        super(new StringBuilder().append("name: ").append(name).toString());
    }
}
