package com.t3t.bookstoreapi.coupon.client;

import com.t3t.bookstoreapi.coupon.model.request.CouponIdRequest;
import com.t3t.bookstoreapi.coupon.model.response.CouponResponse;
import com.t3t.bookstoreapi.model.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "CouponApiClient", url = "${t3t.feignClient.url}")
public interface CouponApiClient {
    @GetMapping("/t3t/coupon/coupons/book")
    ResponseEntity<BaseResponse<CouponResponse>> getBookCoupon();

    @GetMapping("/t3t/coupon/coupons/category")
    ResponseEntity<BaseResponse<CouponResponse>> getCategoryCoupon();

    @GetMapping("/t3t/coupon/coupons/general")
    ResponseEntity<BaseResponse<CouponResponse>> getGeneralCoupon();

    @PutMapping("/t3t/coupon/coupons/book")
    ResponseEntity<BaseResponse<Void>> deleteBookCoupon(@RequestBody CouponIdRequest request);

    @PutMapping("/t3t/coupon/coupons/category")
    ResponseEntity<BaseResponse<Void>> deleteCategoryCoupon(@RequestBody CouponIdRequest request);
}