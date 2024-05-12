package com.t3t.bookstoreapi.book.exception;

public class ConversionException extends RuntimeException {

    /**
     * 지정된 메시지와 원인(Throwable)을 사용하여 ConversionException을 생성
     *
     * @param message 예외에 대한 설명 메시지
     * @param cause   원인 예외
     * @author Yujin-nKim(김유진)
     */
    public ConversionException(String message, Throwable cause) {
        super(message, cause);
    }
}
