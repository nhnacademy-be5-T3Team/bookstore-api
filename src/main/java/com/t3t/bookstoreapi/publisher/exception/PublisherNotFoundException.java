package com.t3t.bookstoreapi.publisher.exception;

/**
 * 존재하지 않는 출판사(Publisher)에 대해 조회를 시도하는 경우 발생하는 예외
 * @author hydrationn(박수화)
 */
public class PublisherNotFoundException extends RuntimeException{
    private static final String DEFAULT_MESSAGE = "존재하지 않는 출판사입니다.";

    public PublisherNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public PublisherNotFoundException(Long publisherId) {
        super(String.format("%s (%s)", publisherId, DEFAULT_MESSAGE));
    }
}
