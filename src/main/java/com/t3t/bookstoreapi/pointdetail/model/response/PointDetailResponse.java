package com.t3t.bookstoreapi.pointdetail.model.response;

import com.t3t.bookstoreapi.pointdetail.model.entity.PointDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

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
