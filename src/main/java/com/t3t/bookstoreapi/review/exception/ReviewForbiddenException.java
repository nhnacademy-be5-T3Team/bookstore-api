package com.t3t.bookstoreapi.review.exception;

public class ReviewForbiddenException extends RuntimeException{
    private static final String DEFAULT_MESSAGE = "리뷰를 작성할 수 없는 주문 상태입니다.";
    public ReviewForbiddenException() {
        super(DEFAULT_MESSAGE);
    }
}
