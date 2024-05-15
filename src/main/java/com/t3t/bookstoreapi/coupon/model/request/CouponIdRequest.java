package com.t3t.bookstoreapi.coupon.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponIdRequest {
    private String couponId;
}
