package com.t3t.bookstoreapi.member.controller;

import com.t3t.bookstoreapi.member.model.request.CouponDetailRequest;
import com.t3t.bookstoreapi.member.model.response.CouponDetailResponse;
import com.t3t.bookstoreapi.member.service.CouponDetailService;
import com.t3t.bookstoreapi.model.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CouponDetailController {
    private final CouponDetailService couponDetailService;

    @GetMapping("/members/coupons")
    public ResponseEntity<BaseResponse<List<CouponDetailResponse>>> getAllCouponDetailByMemberId(@RequestHeader("memberId") Long memberId) {
        /*
        try {
            List<CouponDetailResponse> couponDetails = couponDetailService.getCouponByMemberId(memberId);
            return ResponseEntity.ok(new BaseResponse<>(memberId + "님의 쿠폰 사용 내역입니다. ", couponDetails));
        } catch (MemberNotFoundException e) {
            return ResponseEntity.badRequest().body(new BaseResponse<>());
        }*/
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<List<CouponDetailResponse>>().data(couponDetailService.getAllCouponByMemberId(memberId)));
    }

    @PutMapping("/members/coupons")
    public ResponseEntity<BaseResponse<Void>> useCoupon(@RequestBody CouponDetailRequest couponDetailRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<Void>().message(couponDetailService.deleteCouponDetail(couponDetailRequest)));
    }

    @PostMapping("/members/coupons/book")
    public ResponseEntity<BaseResponse<Void>> saveBookCoupon(@RequestHeader("memberId") Long memberId){
        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<Void>().message(couponDetailService.saveBookCoupon(memberId)));
    }

    @PostMapping("/members/coupons/category")
    public ResponseEntity<BaseResponse<Void>> saveCategoryCoupon(@RequestHeader("memberId") Long memberId){
        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<Void>().message(couponDetailService.saveCategoryCoupon(memberId)));
    }
}