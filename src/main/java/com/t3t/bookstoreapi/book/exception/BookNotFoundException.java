package com.t3t.bookstoreapi.book.exception;

/**
 * 존재하지 않는 책에 대해 조회를 시도하는 경우 발생하는 예외
 * @author woody35545(구건모)
 */
public class BookNotFoundException extends RuntimeException{
    private static final String DEFAULT_MESSAGE = "존재하지 않는 책 입니다.";
    public BookNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    protected BookNotFoundException(String forTarget) {
        super(String.format("%s (%s)", DEFAULT_MESSAGE, forTarget));
    }
}
