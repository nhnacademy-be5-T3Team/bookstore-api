package com.t3t.bookstoreapi.member.service;

import com.t3t.bookstoreapi.member.dto.response.CouponDetailResponse;
import com.t3t.bookstoreapi.member.exception.MemberNotFoundException;
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

    @Transactional(readOnly = true)
    public List<CouponDetailResponse> getCouponByMemberId(Long memberId) {
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
}
