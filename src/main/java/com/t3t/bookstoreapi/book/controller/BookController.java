package com.t3t.bookstoreapi.book.controller;

import com.t3t.bookstoreapi.book.model.response.BookSearchResultDetailResponse;
import com.t3t.bookstoreapi.book.service.BookService;
import com.t3t.bookstoreapi.model.response.BaseResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class BookController {
    private final BookService bookService;

    @GetMapping("/books/{bookId}")
    public ResponseEntity<BaseResponse<BookSearchResultDetailResponse>> getBook(@PathVariable Long bookId) {
        return ResponseEntity.ok(new BaseResponse<BookSearchResultDetailResponse>()
                .data(bookService.getBook(bookId)));
    }
}
