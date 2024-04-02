package com.t3t.bookstoreapi.book.controller;

import com.t3t.bookstoreapi.book.model.response.BookSearchResultDetailResponse;
import com.t3t.bookstoreapi.book.model.response.BookSearchResultResponse;
import com.t3t.bookstoreapi.book.service.BookService;
import com.t3t.bookstoreapi.model.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books/{bookId}")
    public ResponseEntity<BaseResponse<BookSearchResultDetailResponse>> getBook(@PathVariable Long bookId) {
        BookSearchResultDetailResponse res = bookService.getBook(bookId);
        BaseResponse<BookSearchResultDetailResponse> response = BaseResponse.success("message", res);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
