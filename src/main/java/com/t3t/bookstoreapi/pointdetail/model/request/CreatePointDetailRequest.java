package com.t3t.bookstoreapi.pointdetail.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 포인트 상세 정보 생성을 위한 요청 데이터 모델
 * 사용자가 포인트 상세 정보를 생성할 때 필요한 정보를 전달하는 데 사용
 *
 * @author hydrationn(박수화)
 */
@Data
@AllArgsConstructor
public class CreatePointDetailRequest {

    // 포인트 상세 내용
    @NotBlank(message = "포인트 상세 내용이 누락되었습니다. ")
    private String content;

    // 포인트 사용/적립 구분
    @NotNull
    private String pointDetailType;

    // 사용/적립 내역 일자
    @NotNull
    private LocalDateTime pointDetailDate;

    // 포인트 양
    @NotNull
    private BigDecimal pointAmount;
}
