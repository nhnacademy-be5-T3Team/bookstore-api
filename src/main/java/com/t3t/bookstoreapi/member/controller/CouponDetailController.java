package com.t3t.bookstoreapi.member.controller;

import com.t3t.bookstoreapi.member.model.response.CouponDetailResponse;
import com.t3t.bookstoreapi.member.exception.MemberNotFoundException;
import com.t3t.bookstoreapi.member.service.CouponDetailService;
import com.t3t.bookstoreapi.model.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CouponDetailController {
    private final CouponDetailService couponDetailService;

    @GetMapping("/members/{memberId}/coupons")
    public ResponseEntity<BaseResponse<List<CouponDetailResponse>>> getCouponDetailByMemberId(@PathVariable Long memberId) {
        try {
            List<CouponDetailResponse> couponDetails = couponDetailService.getCouponByMemberId(memberId);
            return ResponseEntity.ok(new BaseResponse<>(memberId + "님의 쿠폰 사용 내역입니다. ", couponDetails));
        } catch (MemberNotFoundException e) {
            return ResponseEntity.badRequest().body(new BaseResponse<>());
        }
    }
}