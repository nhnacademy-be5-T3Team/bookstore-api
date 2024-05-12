package com.t3t.bookstoreapi.review.model.response;

import com.t3t.bookstoreapi.book.util.BookImageUtils;
import com.t3t.bookstoreapi.review.model.entity.Review;
import com.t3t.bookstoreapi.review.model.entity.ReviewImage;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 도서별 리뷰 목록 조회시 사용하는 데이터 전송 객체(DTO)
 * @author Yujin-nKim(김유진)
 */
@Getter
@Builder
public class ReviewResponse {
    private String name; // 리뷰 작성자 이름
    private String comment; // 리뷰 내용
    private Integer reviewScore; // 평가 점수
    private LocalDateTime createdAt; // 등록 시간
    private LocalDateTime updatedAt; // 수정 시간
    private List<String> reviewImgUrlList; // 리뷰 이미지 url 리스트

    /**
     * Review 객체를 ReviewResponse 객체로 변환
     * @param review Review 객체
     * @return ReviewResponse 객체
     * @author Yujin-nKim(김유진)
     */
    public static ReviewResponse of(Review review) {
        return ReviewResponse.builder()
                .name(review.getMember().getName())
                .comment(review.getReviewComment())
                .reviewScore(review.getReviewScore())
                .createdAt(review.getReviewCreatedAt())
                .updatedAt(review.getReviewUpdatedAt())
                .reviewImgUrlList(BookImageUtils.setReviewImagePrefix(review.getReviewImageList()
                        .stream().map(ReviewImage::getReviewImageUrl).collect(Collectors.toList())))
                .build();
    }
}
