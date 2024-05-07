package com.t3t.bookstoreapi.book.model.request;

import com.t3t.bookstoreapi.book.annotation.UniqueParticipantMap;
import com.t3t.bookstoreapi.book.model.dto.ParticipantMapDto;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * 책 등록 요청을 나타내는 객체
 * @author Yujin-nKim(김유진)
 */
@Data
@Builder
public class BookRegisterRequest {
    @NotBlank(message = "도서 제목을 입력해주세요.")
    @Size(max = 255, message = "도서 제목은 최대 255자여야 합니다.")
    private String bookTitle; // 도서 제목

    @NotBlank(message = "도서 ISBN을 입력해주세요.")
    @Pattern(regexp = "\\d{13}", message = "도서 ISBN은 13자리의 숫자여야 합니다.")
    private String bookIsbn; // 도서 isbn

    @NotNull(message = "도서 가격을 입력해주세요.")
    @DecimalMin(value = "0.0", message = "도서 가격은 0 이상이어야 합니다.")
    private BigDecimal bookPrice; // 도서 정가

    @DecimalMin(value = "0.0", message = "도서 할인율은 0 이상이어야 합니다.")
    @DecimalMax(value = "99.99", message = "도서 할인율은 100 미만이여야 합니다.")
    private BigDecimal bookDiscountRate; // 도서 할인율

    @NotNull(message = "포장 가능 여부를 입력해주세요.")
    @Min(value = 0, message = "포장 가능 여부는 0(불가능) 또는 1(가능)만 입력할 수 있습니다.")
    @Max(value = 1, message = "포장 가능 여부는 0(불가능) 또는 1(가능)만 입력할 수 있습니다.")
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

    @NotNull(message = "출판사 ID를 입력해주세요.")
    private Long publisherId; // 출판사 id

    @NotEmpty(message = "도서 참여자를 선택해주세요.")
    @UniqueParticipantMap
    private List<ParticipantMapDto> participantMapList; // 도서 참여자 - 참여자 역할 선택 리스트

    @NotNull(message = "도서 썸네일을 선택해주세요.")
    private MultipartFile thumbnailImage; // 도서 썸네일 이미지

    private List<MultipartFile> bookImageList; // 도서 미리보기 이미지

    @NotEmpty(message = "카테고리를 선택해주세요.")
    @Size(max = 10, message = "최대 10개의 카테고리를 선택할 수 있습니다.")
    private List<Integer> categoryList; // 카테고리 id 리스트

    @NotEmpty(message = "태그를 선택해주세요.")
    @Size(max = 10, message = "최대 10개의 태그를 선택할 수 있습니다.")
    private List<Long> tagList; // 태그 id 리스트
}
