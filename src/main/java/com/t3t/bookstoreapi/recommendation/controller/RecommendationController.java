package com.t3t.bookstoreapi.recommendation.controller;

import com.t3t.bookstoreapi.model.response.BaseResponse;
import com.t3t.bookstoreapi.recommendation.model.response.BookInfoBrief;
import com.t3t.bookstoreapi.recommendation.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
public class RecommendationController {
    private final RecommendationService recommendationService;

    @Autowired
    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping("/recommendations/recentlyPublished")
    public ResponseEntity<BaseResponse<List<BookInfoBrief>>> getRecentlyPublishedBooks(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(value = "maxCount", required = false, defaultValue = "10") int maxCount) {

        List<BookInfoBrief> res = recommendationService.getRecentlyPublishedBooks(date, maxCount);

        BaseResponse<List<BookInfoBrief>> response = BaseResponse.success("message", res);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/recommendations/mostLike")
    public ResponseEntity<BaseResponse<List<BookInfoBrief>>> getMostReviewedBooks() {
        List<BookInfoBrief> res = recommendationService.getMostReviewedBooks();

        BaseResponse<List<BookInfoBrief>> response = BaseResponse.success("message", res);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
