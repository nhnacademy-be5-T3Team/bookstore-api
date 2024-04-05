package com.t3t.bookstoreapi.recommendation.controller;

import com.t3t.bookstoreapi.model.response.BaseResponse;
import com.t3t.bookstoreapi.recommendation.model.response.BookInfoBrief;
import com.t3t.bookstoreapi.recommendation.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class RecommendationController {
    private final RecommendationService recommendationService;

    @GetMapping("/recommendations/recentlyPublished")
    public ResponseEntity<BaseResponse<List<BookInfoBrief>>> getRecentlyPublishedBooks(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(value = "maxCount", required = false, defaultValue = "10") int maxCount) {

        return ResponseEntity.ok(new BaseResponse<List<BookInfoBrief>>()
                .data(recommendationService.getRecentlyPublishedBooks(date, maxCount)));
    }

    @GetMapping("/recommendations/mostLike")
    public ResponseEntity<BaseResponse<List<BookInfoBrief>>> getMostReviewedBooks() {

        return ResponseEntity.ok(new BaseResponse<List<BookInfoBrief>>()
                .data(recommendationService.getMostReviewedBooks()));
    }

    @GetMapping("/recommendations/bestSeller")
    public ResponseEntity<BaseResponse<List<BookInfoBrief>>> getBestSellerBooks() {
        return ResponseEntity.ok(new BaseResponse<List<BookInfoBrief>>()
                .data(recommendationService.getBestSellerBooks()));
    }
}
