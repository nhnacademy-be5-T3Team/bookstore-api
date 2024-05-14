package com.t3t.bookstoreapi.coupon.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "CouponApiClient", url = "${t3t.feignClient.url}")
public interface CouponApiClient {
    /*@GetMapping(value = "/t3t/coupon/categories/coupons")
    ResponseEntity<BaseResponse<List<CategoryIdResponse>>> getCategoriesId();

    @GetMapping(value = "/t3t/bookstore/categories/{categoryId}/coupons")
    ResponseEntity<BaseResponse<CategoryIdResponse>> getCategoryId(@PathVariable("categoryId") Integer categoryId);*/

    //todo 1 ) Gateway에서 쿠폰 등록
    
    // todo 2 ) book coupon, category coupon, general coupon FeignClient 이용해 가져오기

    // todo 3 ) 가져온 것 db에 넣어놓기
}