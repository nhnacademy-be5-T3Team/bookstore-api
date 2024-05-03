package com.t3t.bookstoreapi.pointdetail.exception;

public class PointDetailNotFoundException extends RuntimeException{
    private static final String DEFAULT_MESSAGE = "존재하지 않는 포인트 내역입니다.";
    public PointDetailNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public PointDetailNotFoundException(String forTarget) {
        super(String.format("%s (%s)", DEFAULT_MESSAGE, forTarget));
    }
}

