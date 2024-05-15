package com.t3t.bookstoreapi.member.service;

import com.t3t.bookstoreapi.coupon.adapter.CouponAdapter;
import com.t3t.bookstoreapi.member.exception.CouponNotFoundException;
import com.t3t.bookstoreapi.member.exception.MemberNotFoundException;
import com.t3t.bookstoreapi.member.model.entity.CouponDetail;
import com.t3t.bookstoreapi.member.model.entity.Member;
import com.t3t.bookstoreapi.member.model.request.CouponDetailRequest;
import com.t3t.bookstoreapi.member.model.response.CouponDetailResponse;
import com.t3t.bookstoreapi.member.repository.CouponDetailRepository;
import com.t3t.bookstoreapi.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CouponDetailService {

    private final CouponDetailRepository couponDetailRepository;

    private final MemberRepository memberRepository;

    private final CouponAdapter couponAdapter;

    @Transactional(readOnly = true)
    public List<CouponDetailResponse> getAllCouponByMemberId(Long memberId) {
        if(!memberRepository.existsById(memberId)) {
            throw new MemberNotFoundException();
        }

        return couponDetailRepository.findByMemberId(memberId).stream()
                .map(couponDetail -> CouponDetailResponse.builder()
                        .couponId(couponDetail.getCouponId())
                        .useDate(couponDetail.getUseDate())
                        .useType(couponDetail.getUseType())
                        .build())
                .collect(Collectors.toList());
    }

    public String deleteCouponDetail(CouponDetailRequest request){
        CouponDetail couponDetail = couponDetailRepository.findById(request.getCouponId()).orElseThrow(() -> new CouponNotFoundException());
        couponDetail.deleteCoupon();

        return "쿠폰이 사용되었습니다";
    }

    public String saveBookCoupon(Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException());

        CouponDetail couponDetail = CouponDetail.builder()
                .couponId(couponAdapter.getBookCoupon().getCouponId())
                .member(member)
                .useType("issued")
                .build();

        couponDetailRepository.save(couponDetail);
        return "Book Coupon이 저장되었습니다";
    }

    public String saveCategoryCoupon(Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException());

        CouponDetail couponDetail = CouponDetail.builder()
                .couponId(couponAdapter.getCategoryCoupon().getCouponId())
                .member(member)
                .useType("issued")
                .build();

        couponDetailRepository.save(couponDetail);
        return "Category Coupon이 저장되었습니다";
    }

    public String saveGeneralCoupon(Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException());

        CouponDetail couponDetail = CouponDetail.builder()
                .couponId(couponAdapter.getGeneralCoupon().getCouponId())
                .member(member)
                .useType("issued")
                .build();

        couponDetailRepository.save(couponDetail);
        return "일반 Coupon이 저장되었습니다";
    }

    public String saveCoupons(String couponType, Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException());

        String couponId = null;

        if("category_coupon".equals(couponType)){
            couponId = couponAdapter.getCategoryCoupon().getCouponId();
        }else if("book_coupon".equals(couponType)){
            couponId = couponAdapter.getBookCoupon().getCouponId();
        }else{
            couponId = couponAdapter.getGeneralCoupon().getCouponId();
        }

        CouponDetail couponDetail = CouponDetail.builder()
                .couponId(couponId)
                .member(member)
                .useType("issued")
                .build();

        couponDetailRepository.save(couponDetail);
        return "Coupon이 저장되었습니다";
    }
}
