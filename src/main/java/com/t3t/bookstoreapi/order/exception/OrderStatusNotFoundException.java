package com.t3t.bookstoreapi.order.exception;

/**
 * 존재하지 않는 주문 상태(OrderStatus)에 대해 조회를 시도하는 경우 발생하는 예외
 * @author woody35545(구건모)
 */
public class OrderStatusNotFoundException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "존재하지 않는 주문 상태(OrderStatus) 입니다.";

    public OrderStatusNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    protected OrderStatusNotFoundException(String forTarget) {
        super(String.format("%s (%s)", DEFAULT_MESSAGE, forTarget));
    }
}
