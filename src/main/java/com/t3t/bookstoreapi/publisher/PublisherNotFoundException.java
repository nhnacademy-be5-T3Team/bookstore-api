package com.t3t.bookstoreapi.publisher;

public class PublisherNotFoundException extends RuntimeException{
    private static final String DEFAULT_MESSAGE = "존재하지 않는 출한사 입니다.";
    public PublisherNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    protected PublisherNotFoundException(String publisherName) {
        super(String.format("%s (%s)", publisherName, DEFAULT_MESSAGE));
    }
}
