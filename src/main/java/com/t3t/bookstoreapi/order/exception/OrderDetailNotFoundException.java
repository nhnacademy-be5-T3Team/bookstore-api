package com.t3t.bookstoreapi.order.exception;

/**
 * 주문 상세 정보를 찾을 수 없을 때 발생하는 예외
 * @author woody35545(구건모)
 */
public class OrderDetailNotFoundException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "주문 상세 정보를 찾을 수 없습니다.";

    public OrderDetailNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    protected OrderDetailNotFoundException(String forTarget) {
        super(new StringBuilder(DEFAULT_MESSAGE).append(" (").append(forTarget).append(")").toString());
    }
}
