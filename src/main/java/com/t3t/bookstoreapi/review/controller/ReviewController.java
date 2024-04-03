package com.t3t.bookstoreapi.review.controller;

import com.t3t.bookstoreapi.model.response.BaseResponse;
import com.t3t.bookstoreapi.review.model.response.ReviewResponse;
import com.t3t.bookstoreapi.review.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReviewController {
    private final ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/reviews/books/{bookId}")
    public ResponseEntity<BaseResponse<Page<ReviewResponse>>> getReviewsByBookId(@PathVariable Long bookId, Pageable pageable) {
        Page<ReviewResponse> reviewsPage = reviewService.findReviewsByBookId(bookId, pageable);
        BaseResponse<Page<ReviewResponse>> response = BaseResponse.success("message", reviewsPage);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}