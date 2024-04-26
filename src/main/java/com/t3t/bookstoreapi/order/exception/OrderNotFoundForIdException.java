package com.t3t.bookstoreapi.order.exception;

/**
 * 존재하지 않는 주문 식별자에 대해 조회를 시도하는 경우 발생하는 예외
 * @author woody35545(구건모)
 */
public class OrderNotFoundForIdException extends OrderNotFoundException {
    public OrderNotFoundForIdException(Long id) {
        super(new StringBuilder().append("id: ").append(id).toString());
    }
}
