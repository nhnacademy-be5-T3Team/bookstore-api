package com.t3t.bookstoreapi.book.controller;

import com.t3t.bookstoreapi.book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("book/{categoryId}")
    public void getBookByCategoryId(@PathVariable String categoryId) {
        bookService.getBookByCategoryId(categoryId);
    }
}
