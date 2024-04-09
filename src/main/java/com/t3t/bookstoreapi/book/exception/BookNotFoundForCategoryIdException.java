package com.t3t.bookstoreapi.book.exception;

public class BookNotFoundForCategoryIdException extends BookNotFoundException {
    public BookNotFoundForCategoryIdException(Integer categoryId) {
        super(String.format("id: %d", categoryId));
    }
}
