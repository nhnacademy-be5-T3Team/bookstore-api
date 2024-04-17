package com.t3t.bookstoreapi.book.model.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class BookDetailResponse {
    private Long bookId; // 도서 id
    private String name; // 도서 제목
    private BigDecimal price; // 정가
    private BigDecimal discountRate; // 할인율
    private BigDecimal discountedPrice; // 할인가
    private LocalDate published; // 출판일시
    private String publisher; // 출판사 이름
    private Float averageScore; // 평균 평점
    private Integer likeCount; // 좋아요 수
    private String bookIndex; // 도서 목차
    private String bookDesc; // 도서 설명
    private String bookIsbn; // isbn
    private boolean orderAvailableStatus; // 재고 여부 (주문 가능 여부)
    private boolean packagingAvailableStatus; // 포장 가능 여부
    private List<PackagingInfo> packagingInfoList; // 포장 종류 리스트
    private List<TagInfo> tagList; // 연결된 태그 리스트
    private List<CategoryInfo> catgoryInfoList; //연결된 카테고리 리스트
    private String coverImageUrl; // 도서 커버 이미지 url
    private List<String> imageUrlList; // 도서 미리보기 이미지 url
    private List<AuthorInfo> authorInfoList; // 도서 참여자 리스트
}