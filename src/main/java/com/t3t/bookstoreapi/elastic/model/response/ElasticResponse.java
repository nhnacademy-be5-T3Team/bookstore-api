package com.t3t.bookstoreapi.elastic.model.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 검색된 도서의 점보를 담은 데이터 전송 객체
 *  @author parkjonggyeong18(박종경)
 */
@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ElasticResponse {
    private BigDecimal bookId;
    private String name;
    private BigDecimal price;
    private BigDecimal discountRate;
    private BigDecimal discountedPrice;
    private LocalDate published;
    private Float averageScore;
    private BigDecimal likeCount;
    private String publisher;
    private String coverImageUrl;
    private String authorName;
    private String authorRole;
    private float score;
    private BigDecimal categoryId;
    private long count; //검색한 책의 수
}
