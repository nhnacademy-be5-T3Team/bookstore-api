package com.t3t.bookstoreapi.review.exception;

public class ReviewImageNotFoundException extends RuntimeException{
    private static final String DEFAULT_MESSAGE = "존재하지 않는 리뷰 이미지 입니다.";
    public ReviewImageNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}
