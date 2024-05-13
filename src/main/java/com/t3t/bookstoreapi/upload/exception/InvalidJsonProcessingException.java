package com.t3t.bookstoreapi.upload.exception;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Json 데이터를 처리하는 중에 발생하는 예외를 나타내는 클래스
 * @author Yujin-nKim(김유진)
 */
public class InvalidJsonProcessingException extends RuntimeException{
    private static final String DEFAULT_MESSAGE = "Json 처리 중 오류가 발생했습니다.";

    public InvalidJsonProcessingException(JsonProcessingException e) {
        super(DEFAULT_MESSAGE, e);
    }
}
