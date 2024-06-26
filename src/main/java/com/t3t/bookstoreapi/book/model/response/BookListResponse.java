package com.t3t.bookstoreapi.book.model.response;

import com.t3t.bookstoreapi.book.enums.TableStatus;
import com.t3t.bookstoreapi.book.model.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;


/**
 * 도서 전체 목록 조회시 사용하는 데이터 전송 객체(DTO)
 * 책의 정보를 포함하고 있으며, 추가적인 로직을 통해 일부 속성들을 설정
 * @author Yujin-nKim(김유진)
 */
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class BookListResponse {
    private Long id; // 도서 id
    private String isbn; // isbn
    private String bookName; // 도서 제목
    private BigDecimal price; // 정가
    private BigDecimal discountRate; // 할인율
    private LocalDate published; // 출판일시
    private Float averageScore; // 평균 평점
    private Integer likeCount; // 좋아요 수
    private Integer stock; // 재고 수
    private BigDecimal discountedPrice; // 할인가
    private TableStatus packagingAvailableStatus; // 포장 가능 여부
    private TableStatus deletedStatus; // 삭제 여부

    public static BookListResponse of(Book book) {
        return BookListResponse.builder()
                .id(book.getBookId())
                .isbn(book.getBookIsbn())
                .bookName(book.getBookName())
                .price(book.getBookPrice())
                .discountRate(book.getBookDiscount())
                .published(book.getBookPublished())
                .averageScore(book.getBookAverageScore())
                .likeCount(book.getBookLikeCount())
                .stock(book.getBookStock())
                .discountedPrice(getDiscountedPrice(book.getBookDiscount(), book.getBookPrice()))
                .packagingAvailableStatus(book.getBookPackage())
                .deletedStatus(book.getIsDeleted())
                .build();
    }

    // 정가, 할인율로 할인된 가격을 설정하는 메서드
    public static BigDecimal getDiscountedPrice(BigDecimal discountRate, BigDecimal price) {

        BigDecimal discountPercentage = discountRate.divide(BigDecimal.valueOf(100));
        BigDecimal discountAmount = price.multiply(discountPercentage);

        return price.subtract(discountAmount);
    }
}
