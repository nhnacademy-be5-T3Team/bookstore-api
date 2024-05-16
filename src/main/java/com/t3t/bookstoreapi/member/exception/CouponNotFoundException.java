package com.t3t.bookstoreapi.member.exception;

public class CouponNotFoundException extends RuntimeException{
    private static final String DEFAULT_MESSGES = "쿠폰이 없습니다";

    public CouponNotFoundException() {
        super(DEFAULT_MESSGES);
    }

    public CouponNotFoundException(String message) {
        super(message);
    }
}
