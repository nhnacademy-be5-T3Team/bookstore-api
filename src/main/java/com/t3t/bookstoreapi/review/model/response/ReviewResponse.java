package com.t3t.bookstoreapi.review.model.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ReviewResponse {
    private String name; // 리뷰 작성자 이름
    private String comment; // 리뷰 내용
    private Integer reviewScore; // 평가 점수
    private LocalDateTime createdAt; // 등록 시간
    private LocalDateTime updatedAt; // 수정 시간
    private List<String> reviewImgUrlList; // 리뷰 이미지 url 리스트
}
