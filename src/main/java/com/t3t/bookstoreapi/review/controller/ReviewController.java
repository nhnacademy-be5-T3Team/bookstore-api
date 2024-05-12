package com.t3t.bookstoreapi.review.controller;

import com.t3t.bookstoreapi.model.response.BaseResponse;
import com.t3t.bookstoreapi.model.response.PageResponse;
import com.t3t.bookstoreapi.review.model.request.ReviewCommentUpdateRequest;
import com.t3t.bookstoreapi.review.model.request.ReviewRequest;
import com.t3t.bookstoreapi.review.model.response.ReviewResponse;
import com.t3t.bookstoreapi.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

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

    /**
     * 사용자 ID에 해당하는 리뷰 페이지 조회
     * @param memberId 회원 ID
     * @param pageNo   페이지 번호
     * @param pageSize 페이지 크기
     * @param sortBy   정렬 기준
     * @return 주어진 회원 ID에 대한 리뷰 페이지 응답
     * @author Yujin-nKim(김유진)
     */
    @GetMapping("/reviews/members/{memberId}")
    public ResponseEntity<BaseResponse<PageResponse<ReviewResponse>>> findReviewsByMemberId(
            @PathVariable Long memberId,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "reviewCreatedAt", required = false) String sortBy) {

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());

        PageResponse<ReviewResponse> reviewList = reviewService.findReviewsByMemberId(memberId, pageable);

        // 리뷰가 없는 경우 | status code 204 (No Content)
        if (reviewList.getContent().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                new BaseResponse<PageResponse<ReviewResponse>>().data(reviewList)
        );
    }

    /**
     * 특정 회원과 특정 도서에 대한 리뷰가 이미 등록되어 있는지 확인
     * @param memberId 회원 ID
     * @param bookId   도서 ID
     * @return 특정 회원이 특정 도서에 대한 리뷰가 이미 등록되어 있는지 여부
     * @author Yujin-nKim(김유진)
     */
    @GetMapping("/reviews/members/{memberId}/exists")
    public ResponseEntity<BaseResponse<Boolean>> existsReview(@PathVariable Long memberId,
                                                              @RequestParam Long bookId) {

        boolean response = reviewService.existsReview(bookId, memberId);

        return ResponseEntity.status(HttpStatus.OK).body(
                new BaseResponse<Boolean>()
                        .data(response)
                        .message(response ? "이미 등록된 리뷰가 존재합니다." : "리뷰가 존재하지 않습니다."));
    }

    /**
     * 리뷰 생성 요청
     * @param request 리뷰 생성 요청 객체
     * @return 200 OK, 성공 메세지
     * @author Yujin-nKim(김유진)
     */
    @PostMapping("/reviews")
    public ResponseEntity<BaseResponse<Void>> createReview(@ModelAttribute @Valid ReviewRequest request) {

        reviewService.createReview(request);

        return ResponseEntity.status(HttpStatus.OK).body(
                new BaseResponse<Void>().message("리뷰 등록 요청이 정상적으로 처리되었습니다."));
    }

    /**
     * 리뷰 comment 수정 요청
     * @param reviewId 수정할 review ID
     * @param request 리뷰 수정 요청 객체
     * @return 200 OK, 성공 메세지
     * @author Yujin-nKim(김유진)
     */
    @PutMapping("/reviews/{reviewId}/comment")
    public ResponseEntity<BaseResponse<Void>> updateReviewDetail(@PathVariable Long reviewId,
                                                                 @RequestBody ReviewCommentUpdateRequest request) {

        reviewService.updateReviewDetail(reviewId, request);

        return ResponseEntity.status(HttpStatus.OK).body(
                new BaseResponse<Void>().message("리뷰 업데이트 요청이 정상적으로 처리되었습니다."));
    }

    /**
     * 리뷰 score 수정 요청
     * @param reviewId 수정할 review ID
     * @param score 수정할 점수
     * @return 200 OK, 성공 메세지
     * @author Yujin-nKim(김유진)
     */
    @PutMapping("/reviews/{reviewId}/score")
    public ResponseEntity<BaseResponse<Void>> updateReviewScore(@PathVariable Long reviewId,
                                                                @RequestParam Integer score) {

        reviewService.updateReviewScore(reviewId, score);

        return ResponseEntity.status(HttpStatus.OK).body(
                new BaseResponse<Void>().message("리뷰 업데이트 요청이 정상적으로 처리되었습니다."));
    }

    /**
     * 리뷰 이미지 추가 요청
     * @param reviewId 수정할 review ID
     * @param imageList 추가할 이미지
     * @return 200 OK, 성공 메세지
     * @author Yujin-nKim(김유진)
     */
    @PostMapping("/reviews/{reviewId}/image")
    public ResponseEntity<BaseResponse<Void>> addReviewImage(@PathVariable Long reviewId,
                                                             @ModelAttribute List<MultipartFile> imageList) {

        reviewService.addReviewImage(reviewId, imageList);

        return ResponseEntity.status(HttpStatus.OK).body(
                new BaseResponse<Void>().message("리뷰 이미지 추가 요청이 정상적으로 처리되었습니다."));
    }

    /**
     * 리뷰 이미지 삭제 요청
     * @param reviewImageId 삭제할 이미지 id
     * @return 200 OK, 성공 메세지
     * @author Yujin-nKim(김유진)
     */
    @DeleteMapping("/reviews/image")
    public ResponseEntity<BaseResponse<Void>> deleteReviewImage(@RequestParam Long reviewImageId) {

        reviewService.deleteReviewImage(reviewImageId);

        return ResponseEntity.status(HttpStatus.OK).body(
                new BaseResponse<Void>().message("리뷰 이미지 삭제 요청이 정상적으로 처리되었습니다."));
    }
}
