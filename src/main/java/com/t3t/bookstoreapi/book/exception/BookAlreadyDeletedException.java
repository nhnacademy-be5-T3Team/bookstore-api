package com.t3t.bookstoreapi.book.exception;

public class BookAlreadyDeletedException extends RuntimeException{

    private static final String DEFAULT_MESSAGE = "이미 삭제된 도서입니다.";

    public BookAlreadyDeletedException() {
        super(DEFAULT_MESSAGE);
    }
}

