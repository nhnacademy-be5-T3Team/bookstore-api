package com.t3t.bookstoreapi.pointdetail.model.response;

import com.t3t.bookstoreapi.pointdetail.model.entity.PointDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * 포인트 상세 정보에 대한 응답 데이터를 표현하는 클래스
 * 포인트 적립 및 사용 내역을 확인하는 데 사용
 *
 * @author hydrationn(박수화)
 */
@Data
@AllArgsConstructor
@Builder
public class PointDetailResponse {

    private String content;

    private String pointDetailType;

    private LocalDateTime pointDetailDate;

    private BigDecimal pointAmount;

    public static PointDetailResponse of(PointDetail pointDetail) {
        return PointDetailResponse.builder()
                .content(pointDetail.getContent())
                .pointDetailType(pointDetail.getPointDetailType())
                .pointDetailDate(pointDetail.getPointDetailDate())
                .pointAmount(pointDetail.getPointAmount())
                .build();
    }
}
