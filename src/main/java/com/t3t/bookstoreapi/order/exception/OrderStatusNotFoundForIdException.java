package com.t3t.bookstoreapi.order.exception;

/**
 * 주문 상태 식별자로 주문 상태 조회시 해당하는 주문 상태가 존재하지 않는 경우 발생하는 예외
 * @see OrderStatusNotFoundException
 * @author woody35545(구건모)
 */
public class OrderStatusNotFoundForIdException extends OrderStatusNotFoundException {
    public OrderStatusNotFoundForIdException(Long id) {
        super(String.format("id: %d", id));
    }
}
