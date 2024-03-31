package com.t3t.bookstoreapi.book.controller;

import com.t3t.bookstoreapi.book.service.BookCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookCategoryController {
    private final BookCategoryService bookCategoryService;

    @Autowired
    public BookCategoryController(BookCategoryService bookCategoryService) {
        this.bookCategoryService = bookCategoryService;
    }

    @GetMapping("/categories/{categoryId}/books")
    public void getBooksByCategoryId(@PathVariable Integer categoryId) {
        bookCategoryService.findBooksByCategoryId(categoryId);
    }
}
