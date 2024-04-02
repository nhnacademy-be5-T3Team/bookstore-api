package com.t3t.bookstoreapi.order.exception;

/**
 * 주문 상태 이름으로 주문 상태 조회시 해당하는 주문 상태가 존재하지 않는 경우 발생하는 예외
 * @see OrderStatusNotFoundException
 * @author woody35545(구건모)
 */
public class OrderStatusNotFoundForNameException extends OrderStatusNotFoundException {
    public OrderStatusNotFoundForNameException(String name) {
        super(String.format("name: %s", name));
    }
}
