package com.t3t.bookstoreapi.member.model.response;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@EqualsAndHashCode
public class CouponDetailResponse {

    private String couponId;

    private LocalDateTime useDate;

    private String useType;

}
