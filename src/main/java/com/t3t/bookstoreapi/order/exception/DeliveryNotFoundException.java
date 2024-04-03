package com.t3t.bookstoreapi.order.exception;

/**
 * 존재하지 않는 배송(Delivery)에 대해 조회를 시도하는 경우 발생하는 예외
 * @author woody35545(구건모)
 */
public class DeliveryNotFoundException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "존재하지 않는 배송(Delivery) 입니다.";

    public DeliveryNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    protected DeliveryNotFoundException(String forTarget) {
        super(String.format("%s (%s)", DEFAULT_MESSAGE, forTarget));
    }
}
