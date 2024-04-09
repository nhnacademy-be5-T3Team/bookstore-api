package com.t3t.bookstoreapi.shoppingcart.exception;

/**
 * 존재하지 않는 장바구니에 대해 조회를 시도하는 경우 발생하는 예외
 * @author woody35545(구건모)
 */
public class ShoppingCartNotFoundException extends RuntimeException{
    private static final String DEFAULT_MESSAGE = "존재하지 않는 장바구니 입니다.";
    public ShoppingCartNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    protected ShoppingCartNotFoundException(String forTarget) {
        super(String.format("%s (%s)", DEFAULT_MESSAGE, forTarget));
    }
}
