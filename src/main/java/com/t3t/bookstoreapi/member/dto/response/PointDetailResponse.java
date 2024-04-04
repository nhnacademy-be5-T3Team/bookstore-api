package com.t3t.bookstoreapi.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class PointDetailResponse {

    private Long pointDetailId;

    private String content;

    private String pointDetailType;

    private LocalDateTime pointDetailDate;

    private BigDecimal pointAmount;

}
