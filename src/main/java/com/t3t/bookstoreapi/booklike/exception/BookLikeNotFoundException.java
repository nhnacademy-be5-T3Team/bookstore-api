package com.t3t.bookstoreapi.booklike.exception;

public class BookLikeNotFoundException extends RuntimeException{
    private static final String DEFAULT_MESSAGE = "존재하지 않는 데이터 입니다.";
    public BookLikeNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    protected BookLikeNotFoundException(String forTarget) {
        super(String.format("%s (%s)", DEFAULT_MESSAGE, forTarget));
    }
}
