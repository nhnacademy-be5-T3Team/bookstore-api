package com.t3t.bookstoreapi.publisher.exception;

public class PublisherNotFoundException extends RuntimeException{
    private static final String DEFAULT_MESSAGE = "존재하지 않는 출판사입니다.";

    public PublisherNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public PublisherNotFoundException(Long publisherId) {
        super(String.format("%s (%s)", publisherId, DEFAULT_MESSAGE));
    }
}
