package com.t3t.bookstoreapi.category.exception;

public class CategoryNotFoundException extends RuntimeException{
    private static final String DEFAULT_MESSAGE = "존재하지 않는 카테고리 입니다.";
    public CategoryNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}
