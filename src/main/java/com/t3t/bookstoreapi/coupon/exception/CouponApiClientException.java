package com.t3t.bookstoreapi.coupon.exception;

public class CouponApiClientException extends RuntimeException{
    private static final String DEFAULT_MESSAGES = "쿠폰을 가져오지 못했습니다";
    public CouponApiClientException(String message) {
        super(message);
    }
    public CouponApiClientException(){
        super(DEFAULT_MESSAGES);
    }
}
