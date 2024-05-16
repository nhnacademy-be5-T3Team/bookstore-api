package com.t3t.bookstoreapi.review.controller;

import com.t3t.bookstoreapi.model.response.BaseResponse;
import com.t3t.bookstoreapi.model.response.PageResponse;
import com.t3t.bookstoreapi.review.model.request.ReviewCommentUpdateRequest;
import com.t3t.bookstoreapi.review.model.request.ReviewRegisterRequest;
import com.t3t.bookstoreapi.review.model.response.ReviewResponse;
import com.t3t.bookstoreapi.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Slf4j
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
     * 리뷰 상세 조회
     * @param reviewId 리뷰 ID
     * @return 리뷰 상세
     * @author Yujin-nKim(김유진)
     */
    @GetMapping("/reviews/{reviewId}")
    public ResponseEntity<BaseResponse<ReviewResponse>> findReviewByReviewId(@PathVariable Long reviewId) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new BaseResponse<ReviewResponse>().data(reviewService.findReviewByReviewId(reviewId)));
    }

    /**
     * 리뷰가 작성 가능한 지 확인
     * @param memberId 회원 ID
     * @param bookId   도서 ID
     * @param orderDetailId 주문상세 ID
     * @return 리뷰 작성 가능 여부
     * @author Yujin-nKim(김유진)
     */
    @GetMapping("/reviews/members/{memberId}/capability")
    public ResponseEntity<BaseResponse<Boolean>> checkReviewCapability(@PathVariable Long memberId,
                                                              @RequestParam Long bookId, @RequestParam Long orderDetailId) {

        boolean response = reviewService.checkReviewCapability(bookId, memberId, orderDetailId);

        return ResponseEntity.status(HttpStatus.OK).body(
                new BaseResponse<Boolean>()
                        .data(response)
                        .message(response ? "리뷰를 등록할 수 있습니다." : "리뷰를 등록할 수 없습니다."));
    }

    /**
     * 리뷰 생성 요청
     * @param request 리뷰 생성 요청 객체
     * @return 200 OK, 성공 메세지
     * @author Yujin-nKim(김유진)
     */
    @PostMapping("/reviews")
    public ResponseEntity<BaseResponse<Void>> createReview(@ModelAttribute @Valid ReviewRegisterRequest request) {

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
                                                             @RequestPart List<MultipartFile> imageList) {

        reviewService.addReviewImage(reviewId, imageList);

        return ResponseEntity.status(HttpStatus.OK).body(
                new BaseResponse<Void>().message("리뷰 이미지 추가 요청이 정상적으로 처리되었습니다."));
    }

    /**
     * 리뷰 이미지 삭제 요청
     * @param reviewImageName 삭제할 이미지 name
     * @return 200 OK, 성공 메세지
     * @author Yujin-nKim(김유진)
     */
    @DeleteMapping("/reviews/image")
    public ResponseEntity<BaseResponse<Void>> deleteReviewImage(@RequestParam String reviewImageName) {

        reviewService.deleteReviewImage(reviewImageName);

        return ResponseEntity.status(HttpStatus.OK).body(
                new BaseResponse<Void>().message("리뷰 이미지 삭제 요청이 정상적으로 처리되었습니다."));
    }
}
