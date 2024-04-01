package com.t3t.bookstoreapi.book.model.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder
public class BookSearchResultResponse {
    private String name;
    private BigDecimal price;
    private BigDecimal discount;
    private LocalDate published;
    private Float averageScore;
    private Integer likeCount;
    private String publisher;
    private String coverImageUrl;
    private String author;
    private String authorType;
}
