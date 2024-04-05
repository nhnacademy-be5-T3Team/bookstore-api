package com.t3t.bookstoreapi.category.controller;

import com.t3t.bookstoreapi.category.model.response.CategoryListResponse;
import com.t3t.bookstoreapi.category.service.CategoryService;
import com.t3t.bookstoreapi.model.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/categories")
    public ResponseEntity<BaseResponse<List<CategoryListResponse>>> getCategoriesHierarchy() {
        return ResponseEntity.ok(new BaseResponse<List<CategoryListResponse>>()
                .data(categoryService.getCategoriesHierarchy()));
    }
}
