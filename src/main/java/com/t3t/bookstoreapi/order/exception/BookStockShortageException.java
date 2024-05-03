package com.t3t.bookstoreapi.order.exception;

/**
 * 책 재고가 부족할 때 발생하는 예외
 * @auhtor woody35545(구건모)
 */
public class BookStockShortageException extends RuntimeException{
    public BookStockShortageException(String message) {
        super(message);
    }
}
