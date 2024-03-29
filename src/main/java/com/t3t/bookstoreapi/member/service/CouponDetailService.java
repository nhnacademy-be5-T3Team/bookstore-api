package com.t3t.bookstoreapi.member.service;

import com.t3t.bookstoreapi.member.dto.response.CouponDetailResponse;
import com.t3t.bookstoreapi.member.exception.MemberNotFoundException;
import com.t3t.bookstoreapi.member.repository.CouponDetailRepository;
import com.t3t.bookstoreapi.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CouponDetailService {

    private final CouponDetailRepository couponDetailRepository;
    private final MemberRepository memberRepository;

    public List<CouponDetailResponse> getCouponByMemberId(Long memberId) {
        if(!memberRepository.existsById(memberId)) {
            throw new MemberNotFoundException("회원을 찾을 수 없습니다. ");
        }

        return couponDetailRepository.findByMemberId(memberId).stream()
                .map(couponDetail -> CouponDetailResponse.builder()
                        .couponId(couponDetail.getCouponId())
                        .useDate(couponDetail.getUseDate())
                        .useType(couponDetail.getUseType())
                        .build())
                .collect(Collectors.toList());
    }
}
