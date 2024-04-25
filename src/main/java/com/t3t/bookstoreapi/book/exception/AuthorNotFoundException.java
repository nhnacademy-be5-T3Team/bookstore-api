package com.t3t.bookstoreapi.book.exception;

public class AuthorNotFoundException extends RuntimeException{
    private static final String DEFAULT_MESSAGE = "존재하지 않는 도서 참여자 입니다.";
    public AuthorNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    protected AuthorNotFoundException(String forTarget) {
        super(String.format("%s (%s)", DEFAULT_MESSAGE, forTarget));
    }
}
