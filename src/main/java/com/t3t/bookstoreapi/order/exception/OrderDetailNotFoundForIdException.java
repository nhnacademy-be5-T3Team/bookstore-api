package com.t3t.bookstoreapi.order.exception;

/**
 * 존재하지 않는 식별자로 주문 상세 정보를 찾을 때 발생하는 예외
 *
 * @author woody35545(구건모)
 */
public class OrderDetailNotFoundForIdException extends OrderDetailNotFoundException {
    public OrderDetailNotFoundForIdException(long orderDetailId) {
        super(new StringBuilder("id: ").append(orderDetailId).toString());
    }
}
