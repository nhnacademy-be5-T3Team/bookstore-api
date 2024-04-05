package com.t3t.bookstoreapi.book.controller;

import com.t3t.bookstoreapi.book.model.response.BookSearchResultResponse;
import com.t3t.bookstoreapi.book.service.BookCategoryService;
import com.t3t.bookstoreapi.model.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class BookCategoryController {
    private final BookCategoryService bookCategoryService;


    @GetMapping("/category/{categoryId}/books")
    public ResponseEntity<BaseResponse<Page<BookSearchResultResponse>>> getBooksByCategoryId(@PathVariable Integer categoryId, Pageable pageable) {
        return ResponseEntity.ok(new BaseResponse<Page<BookSearchResultResponse>>()
                .data(bookCategoryService.findBooksByCategoryId(categoryId, pageable)));
    }
}
