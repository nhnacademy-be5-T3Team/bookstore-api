package com.t3t.bookstoreapi.member.model.request;

import lombok.*;

@Builder
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class CouponDetailRequest {
    private String couponId;
}
