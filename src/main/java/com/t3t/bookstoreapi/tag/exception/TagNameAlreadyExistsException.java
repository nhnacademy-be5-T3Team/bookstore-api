package com.t3t.bookstoreapi.tag.exception;

public class TagNameAlreadyExistsException extends RuntimeException{

    private static final String DEFAULT_MESSAGE = "이미 등록된 태그 이름입니다.";

    public TagNameAlreadyExistsException() {
        super(DEFAULT_MESSAGE);
    }
}
