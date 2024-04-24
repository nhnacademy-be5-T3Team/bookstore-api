package com.t3t.bookstoreapi.book.model.response;

import com.t3t.bookstoreapi.book.enums.TableStatus;
import com.t3t.bookstoreapi.book.model.dto.CategoryDto;
import com.t3t.bookstoreapi.book.model.dto.PackagingDto;
import com.t3t.bookstoreapi.book.model.dto.ParticipantRoleRegistrationDto;
import com.t3t.bookstoreapi.book.model.dto.TagDto;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * 책의 상세 정보를 담은 데이터 전송 객체(DTO)
 * 책의 여러 속성과 연결된 정보를 포함하고 있으며, 추가적인 로직을 통해 일부 속성들을 설정
 * @author Yujin-nKim(김유진)
 */
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class BookDetailResponse {
    private Long id; // 도서 id
    private String isbn; // isbn
    private String bookName; // 도서 제목
    private String index; // 도서 목차
    private String desc; // 도서 설명
    private BigDecimal price; // 정가
    private BigDecimal discountRate; // 할인율
    private LocalDate published; // 출판일시
    private Float averageScore; // 평균 평점
    private Integer likeCount; // 좋아요 수
    private Integer stock; // 재고 수

    private BigDecimal discountedPrice; // 할인가
    private boolean orderAvailableStatus; // 재고 여부 (주문 가능 여부)
    private TableStatus packagingAvailableStatus; // 포장 가능 여부

    private String publisherName; // 출판사 이름
    private String thumbnailImageUrl; // 도서 썸네일 이미지 url

    private List<PackagingDto> packagingInfoList; // 포장 종류 리스트

    private List<String> bookImageUrlList; // 도서 미리보기 이미지 url
    private List<CategoryDto> categoryList; //연결된 카테고리 리스트
    private List<TagDto> tagList; // 연결된 태그 리스트
    private List<ParticipantRoleRegistrationDto> participantList; // 도서 참여자 리스트

    // 정가, 할인율로 할인된 가격을 설정하는 메서드
    public void setDiscountedPrice() {

        BigDecimal discountPercentage = discountRate.divide(BigDecimal.valueOf(100));
        BigDecimal discountAmount = price.multiply(discountPercentage);

        discountedPrice = price.subtract(discountAmount);
    }

    // 재고 값을 통해 주문 가능 여부를 설정하는 메서드
    public void setBookStock() {
        orderAvailableStatus = stock > 0;
    }

    // 포장 정보 리스트를 설정하는 메서드
    public void setPackaingInfoList(List<PackagingDto> packagingDtos) {
        this.packagingInfoList = packagingDtos;
    }
}