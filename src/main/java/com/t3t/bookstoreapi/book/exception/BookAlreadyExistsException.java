package com.t3t.bookstoreapi.book.exception;

public class BookAlreadyExistsException extends RuntimeException{

    private static final String DEFAULT_MESSAGE = "이미 등록된 도서입니다.";

    public BookAlreadyExistsException() {
        super(DEFAULT_MESSAGE);
    }
}
