package com.t3t.bookstoreapi.review.controller;

import com.t3t.bookstoreapi.model.response.BaseResponse;
import com.t3t.bookstoreapi.model.response.PageResponse;
import com.t3t.bookstoreapi.review.model.response.ReviewResponse;
import com.t3t.bookstoreapi.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ReviewController {
    private final ReviewService reviewService;

    /**
     * 책 ID에 해당하는 리뷰 페이지 조회
     * @param bookId   리뷰를 검색할 책의 ID
     * @param pageNo   페이지 번호
     * @param pageSize 페이지 크기
     * @param sortBy   정렬 기준
     * @return 주어진 책 ID에 대한 리뷰 페이지 응답
     * @author Yujin-nKim(김유진)
     */
    @GetMapping("/reviews/books/{bookId}")
    public ResponseEntity<BaseResponse<PageResponse<ReviewResponse>>> findReviewsByBookId(
            @PathVariable Long bookId,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "6", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "reviewCreatedAt", required = false) String sortBy) {

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());

        PageResponse<ReviewResponse> reviewList = reviewService.findReviewsByBookId(bookId, pageable);

        // 도서에 등록된 리뷰가 없는 경우 | status code 204 (No Content)
        if (reviewList.getContent().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                new BaseResponse<PageResponse<ReviewResponse>>().data(reviewList)
        );
    }
}
