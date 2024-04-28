package com.t3t.bookstoreapi.order.exception;

/**
 * 존재하지 않는 주문에 대해 조회를 시도하는 경우 발생하는 예외
 * @author woody35545(구건모)
 */
public class OrderNotFoundException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "존재하지 않는 주문입니다.";

    public OrderNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    protected OrderNotFoundException(String forTarget) {
        super(new StringBuilder().append(DEFAULT_MESSAGE).append(" (").append(forTarget).append(")").toString());
    }
}
