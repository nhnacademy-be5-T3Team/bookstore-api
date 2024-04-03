package com.t3t.bookstoreapi.shoppingcart.exception;

/**
 * 존재하지 않는 장바구니 상세 항목 식별자에 대해 조회를 시도하는 경우 발생하는 예외
 * @see ShoppingCartNotFoundException
 * @author woody35545(구건모)
 */
public class ShoppingCartDetailNotFoundForIdException extends ShoppingCartNotFoundException{
    public ShoppingCartDetailNotFoundForIdException(Long id) {
        super(String.format("id: %d", id));
    }
}
