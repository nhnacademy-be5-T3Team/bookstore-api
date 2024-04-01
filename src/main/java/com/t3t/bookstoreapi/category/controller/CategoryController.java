package com.t3t.bookstoreapi.category.controller;

import com.t3t.bookstoreapi.category.service.CategoryService;
import com.t3t.bookstoreapi.model.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public ResponseEntity<BaseResponse<Map<String, Object>>> getCategoriesHierarchy() {
        Map<String, Object> responseData = categoryService.getCategoriesHierarchy();
        BaseResponse<Map<String, Object>> response = BaseResponse.success("success", responseData);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
