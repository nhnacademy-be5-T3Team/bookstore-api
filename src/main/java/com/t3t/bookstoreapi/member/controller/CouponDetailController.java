package com.t3t.bookstoreapi.member.controller;

import com.t3t.bookstoreapi.member.model.request.CouponDetailRequest;
import com.t3t.bookstoreapi.member.model.response.CouponDetailResponse;
import com.t3t.bookstoreapi.member.service.CouponDetailService;
import com.t3t.bookstoreapi.model.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CouponDetailController {
    private final CouponDetailService couponDetailService;

    @GetMapping("/members/coupons")
    public ResponseEntity<BaseResponse<List<CouponDetailResponse>>> getAllCouponDetailByMemberId(HttpServletRequest request) {
        /*
        try {
            List<CouponDetailResponse> couponDetails = couponDetailService.getCouponByMemberId(memberId);
            return ResponseEntity.ok(new BaseResponse<>(memberId + "님의 쿠폰 사용 내역입니다. ", couponDetails));
        } catch (MemberNotFoundException e) {
            return ResponseEntity.badRequest().body(new BaseResponse<>());
        }*/
        Long memberId = Long.valueOf(request.getHeader("memberId"));
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<List<CouponDetailResponse>>().data(couponDetailService.getAllCouponByMemberId(memberId)));
    }

    @PutMapping("/members/coupons")
    public ResponseEntity<BaseResponse<Void>> useCoupon(@RequestBody CouponDetailRequest couponDetailRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse<Void>().message(couponDetailService.deleteCouponDetail(couponDetailRequest)));
    }

    /**
     * 회원이 도서 쿠폰 발급받기 위해 사용하는 서비스
     * @author joohyun1996(이주현)
     */
    @PostMapping("/members/coupons/book")
    public ResponseEntity<BaseResponse<Void>> saveBookCoupon(HttpServletRequest request){
        Long memberId = Long.valueOf(request.getHeader("memberId"));
        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<Void>().message(couponDetailService.saveBookCoupon(memberId)));
    }

    /**
     * 회원이 카테고리 쿠폰 발급받기 위해 사용하는 서비스
     * @author joohyun1996(이주현)
     */
    @PostMapping("/members/coupons/category")
    public ResponseEntity<BaseResponse<Void>> saveCategoryCoupon(HttpServletRequest request){
        Long memberId = Long.valueOf(request.getHeader("memberId"));
        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<Void>().message(couponDetailService.saveCategoryCoupon(memberId)));
    }

    /**
     * 관리자가 회원에게 쿠폰 지급하기 위해 사용하는 서비스
     * @param couponType
     * @param memberId
     * @author joohyun1996(이주현)
     */
    @PostMapping("/members/coupons/{memberId}/{couponType}")
    public ResponseEntity<BaseResponse<Void>> saveCoupons(@PathVariable("couponType") String couponType,
                                                          @PathVariable("memberId") Long memberId){
        return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse<Void>().message(couponDetailService.saveCoupons(couponType, memberId)));
    }
}