package com.t3t.bookstoreapi.shoppingcart.exception;

/**
 * 존재하지 않는 장바구니 상세 항목 식별자에 대해 조회를 시도하는 경우 발생하는 예외
 * @author woody35545(구건모)
 */
public class ShoppingCartDetailNotFoundException extends RuntimeException{
    private static final String DEFAULT_MESSAGE = "존재하지 않는 장바구니 항목입니다.";
    public ShoppingCartDetailNotFoundException(Long id) {
        super(DEFAULT_MESSAGE);
    }

    protected ShoppingCartDetailNotFoundException(String forTarget) {
        super(String.format("%s (%s)", DEFAULT_MESSAGE, forTarget));
    }
}
