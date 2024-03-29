package com.t3t.bookstoreapi.member.dto.response;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class CouponDetailResponse {

    private String couponId;

    private LocalDateTime useDate;

    private String useType;

}
