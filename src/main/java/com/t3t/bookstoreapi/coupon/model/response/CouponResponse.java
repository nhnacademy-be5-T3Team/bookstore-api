package com.t3t.bookstoreapi.coupon.model.response;

import lombok.*;

@Builder
@Getter@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class CouponResponse {
    private String couponId;
}
