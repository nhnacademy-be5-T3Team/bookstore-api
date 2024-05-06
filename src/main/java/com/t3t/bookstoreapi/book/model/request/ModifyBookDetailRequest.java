package com.t3t.bookstoreapi.book.model.request;

import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class ModifyBookDetailRequest {
    @NotBlank(message = "도서 제목을 입력해주세요.")
    private String bookTitle; // 도서 제목

    @NotBlank(message = "도서 ISBN을 입력해주세요.")
    private String bookIsbn; // 도서 isbn

    @NotNull(message = "도서 가격을 입력해주세요.")
    @DecimalMin(value = "0.0", message = "도서 가격은 0 이상이어야 합니다.")
    private BigDecimal bookPrice; // 도서 정가

    @DecimalMin(value = "0.0", message = "도서 할인율은 0 이상이어야 합니다.")
    @DecimalMax(value = "99.99", message = "도서 할인율은 100 미만이여야 합니다.")
    private BigDecimal bookDiscountRate; // 도서 할인율

    @NotNull(message = "포장 가능 여부를 입력해주세요.")
    private Integer packagingAvailableStatus; // 포장 가능 여부

    @NotNull(message = "도서 출판일을 입력해주세요.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate bookPublished; // 도서 출판일

    @NotNull(message = "도서 재고를 입력해주세요.")
    @Min(value = 0, message = "도서 재고는 0 이상이어야 합니다.")
    private Integer bookStock; // 재고

    @NotBlank
    private String bookIndex; // 도서 목차

    @NotBlank
    private String bookDesc; // 도서 설명
}
