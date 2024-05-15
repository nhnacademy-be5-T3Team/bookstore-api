package com.t3t.bookstoreapi.tag.exception;

public class TagNotFoundException extends RuntimeException{

    private static final String DEFAULT_MESSAGE = "존재하지 않는 태그 입니다.";

    public TagNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}
