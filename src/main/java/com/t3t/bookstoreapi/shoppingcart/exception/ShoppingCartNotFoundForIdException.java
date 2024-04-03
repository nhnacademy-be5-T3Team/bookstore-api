package com.t3t.bookstoreapi.shoppingcart.exception;

/**
 * 존재하지 않는 장바구니 식별자에 대해 조회를 시도하는 경우 발생하는 예외
 * @see ShoppingCartNotFoundException
 * @author woody35545(구건모)
 */
public class ShoppingCartNotFoundForIdException extends ShoppingCartNotFoundException {
    public ShoppingCartNotFoundForIdException(Long id) {
        super(String.format("id: %d", id));
    }
}
