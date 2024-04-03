package com.t3t.bookstoreapi.order.exception;

/**
 * 존재하지 않는 배송(Delivery)에 대해 조회를 시도하는 경우 발생하는 예외
 * @see DeliveryNotFoundException
 * @author woody35545(구건모)
 */
public class DeliveryNotFoundForIdException extends DeliveryNotFoundException{
    public DeliveryNotFoundForIdException(Long id) {
        super(String.format("id: %d", id));
    }
}
