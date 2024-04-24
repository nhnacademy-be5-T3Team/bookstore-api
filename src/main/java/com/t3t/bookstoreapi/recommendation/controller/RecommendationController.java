package com.t3t.bookstoreapi.recommendation.controller;

import com.t3t.bookstoreapi.model.response.BaseResponse;
import com.t3t.bookstoreapi.recommendation.model.response.BookInfoBriefResponse;
import com.t3t.bookstoreapi.recommendation.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class RecommendationController {
    private final RecommendationService recommendationService;

    /**
     * 특정 날짜를 기준으로 일주일 이내에 출판된 도서 목록을 반환
     *
     * @param date      조회할 도서 출판 날짜
     * @param maxCount  최대 반환 도서 수 (기본값: 10)
     * @return BookInfoBrief 객체로 이루어진 리스트 (도서 목록 정보)
     * @author Yujin-nKim(김유진)
     */
    @GetMapping("/recommendations/recentlyPublished")
    public ResponseEntity<BaseResponse<List<BookInfoBriefResponse>>> getRecentlyPublishedBooks(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @NotNull LocalDate date,
            @RequestParam(value = "maxCount", required = false, defaultValue = "10") @Min(1) @Max(100) int maxCount) {

        List<BookInfoBriefResponse> bookInfoBriefResponseList = recommendationService.getRecentlyPublishedBooks(date, maxCount);

        if(bookInfoBriefResponseList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                    new BaseResponse<List<BookInfoBriefResponse>>().message("최근에 출판된 도서 목록이 없습니다."));
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new BaseResponse<List<BookInfoBriefResponse>>().data(bookInfoBriefResponseList));
    }

    /**
     * 좋아요 수와 평점이 높은 도서 목록을 반환
     *
     * @param maxCount  최대 반환 도서 수 (기본값: 10)
     * @return BookInfoBrief 객체로 이루어진 리스트 (도서 목록 정보)
     * @author Yujin-nKim(김유진)
     */
    @GetMapping("/recommendations/mostLike")
    public ResponseEntity<BaseResponse<List<BookInfoBriefResponse>>> getBooksByMostLikedAndHighAverageScore(
            @RequestParam(value = "maxCount", required = false, defaultValue = "10") @Min(1) @Max(100) int maxCount) {

        List<BookInfoBriefResponse> bookInfoBriefResponseList = recommendationService.getBooksByMostLikedAndHighAverageScore(maxCount);

        if(bookInfoBriefResponseList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                    new BaseResponse<List<BookInfoBriefResponse>>().message("인기 도서 목록이 없습니다."));
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new BaseResponse<List<BookInfoBriefResponse>>().data(bookInfoBriefResponseList));
    }

    /**
     * 베스트 셀러 도서 목록을 반환
     *
     * @param maxCount  최대 반환 도서 수 (기본값: 10)
     * @return BookInfoBrief 객체로 이루어진 리스트 (도서 목록 정보)
     * @author Yujin-nKim(김유진)
     */
    @GetMapping("/recommendations/bestSeller")
    public ResponseEntity<BaseResponse<List<BookInfoBriefResponse>>> getBestSellerBooks(
            @RequestParam(value = "maxCount", required = false, defaultValue = "10") @Min(1) @Max(100) int maxCount) {

        List<BookInfoBriefResponse> bookInfoBriefResponseList = recommendationService.getBestSellerBooks(maxCount);

        if(bookInfoBriefResponseList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                    new BaseResponse<List<BookInfoBriefResponse>>().message("베스트 셀러 도서 목록이 없습니다."));
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new BaseResponse<List<BookInfoBriefResponse>>().data(bookInfoBriefResponseList));
    }
}
