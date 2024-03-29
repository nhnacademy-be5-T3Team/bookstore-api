package com.t3t.bookstoreapi.member.controller;

import com.t3t.bookstoreapi.member.dto.response.CouponDetailResponse;
import com.t3t.bookstoreapi.member.exception.MemberNotFoundException;
import com.t3t.bookstoreapi.member.service.CouponDetailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/members")
public class CouponController {
    private final CouponDetailService couponDetailService;

    public CouponController(CouponDetailService couponDetailService) {
        this.couponDetailService = couponDetailService;
    }

    // 회원 쿠폰 사용 이력 조회
    @GetMapping("/members/{memberId}/coupons")
    public ResponseEntity<?> getCouponDetail(@PathVariable Long memberId) {
        try {
            List<CouponDetailResponse> couponDetails = couponDetailService.getCouponByMemberId(memberId);
            return ResponseEntity.ok(couponDetails);
        } catch (MemberNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
