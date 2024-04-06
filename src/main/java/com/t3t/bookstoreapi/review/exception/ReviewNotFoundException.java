package com.t3t.bookstoreapi.review.exception;

public class ReviewNotFoundException extends RuntimeException{
    private static final String DEFAULT_MESSAGE = "존재하지 않는 리뷰 입니다.";
    public ReviewNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    protected ReviewNotFoundException(String forTarget) {
        super(String.format("%s (%s)", DEFAULT_MESSAGE, forTarget));
    }
}
