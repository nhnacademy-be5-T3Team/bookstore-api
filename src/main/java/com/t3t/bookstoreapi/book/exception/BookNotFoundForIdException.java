package com.t3t.bookstoreapi.book.exception;

/**
 * 존재하지 않는 책 식별자에 대해 조회를 시도하는 경우 발생하는 예외
 * @see BookNotFoundException
 * @author woody35545(구건모)
 */
public class BookNotFoundForIdException extends BookNotFoundException{
    public BookNotFoundForIdException(Long id) {
        super(String.format("id: %d", id));
    }
}
