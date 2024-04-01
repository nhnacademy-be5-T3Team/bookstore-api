package com.t3t.bookstoreapi.book.controller;

import com.t3t.bookstoreapi.book.model.response.BookSearchResultResponse;
import com.t3t.bookstoreapi.book.service.BookCategoryService;
import com.t3t.bookstoreapi.model.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookCategoryController {
    private final BookCategoryService bookCategoryService;

    @Autowired
    public BookCategoryController(BookCategoryService bookCategoryService) {
        this.bookCategoryService = bookCategoryService;
    }

    @GetMapping("/categories/{categoryId}/books")
    public ResponseEntity<BaseResponse<List<BookSearchResultResponse>>> getBooksByCategoryId(@PathVariable Integer categoryId) {
        List<BookSearchResultResponse> books = bookCategoryService.findBooksByCategoryId(categoryId);
        BaseResponse<List<BookSearchResultResponse>> response = BaseResponse.success("message", books);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
