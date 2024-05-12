package com.t3t.bookstoreapi.review.exception;

public class ReviewAlreadyExistsException extends RuntimeException{
    private static final String DEFAULT_MESSAGE = "이미 등록된 도서입니다.";

    public ReviewAlreadyExistsException() {
        super(DEFAULT_MESSAGE);
    }
}
