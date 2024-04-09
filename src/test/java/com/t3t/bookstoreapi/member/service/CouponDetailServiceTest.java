package com.t3t.bookstoreapi.member.service;

import com.t3t.bookstoreapi.member.model.entity.CouponDetail;
import com.t3t.bookstoreapi.member.model.entity.Member;
import com.t3t.bookstoreapi.member.model.response.CouponDetailResponse;
import com.t3t.bookstoreapi.member.repository.CouponDetailRepository;
import com.t3t.bookstoreapi.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("prod")
public class CouponDetailServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CouponDetailRepository couponDetailRepository;

    @Autowired
    private CouponDetailService couponDetailService;

    @Test
    void getCouponByMemberId() {
        Long memberId = 1L;
        Member member = memberRepository.findById(memberId).orElse(null);
        memberRepository.save(member);

        CouponDetail couponDetail1 = couponDetailRepository.save(CouponDetail.builder()
                .couponId("1001")
                .member(member)
                .useDate(LocalDateTime.of(2024, 3, 31, 0, 0, 0))
                .useType("used")
                .build());
        CouponDetail couponDetail2 = couponDetailRepository.save(CouponDetail.builder()
                .couponId("1002")
                .member(member)
                .useDate(null)
                .useType("issued")
                .build());

        List<CouponDetailResponse> couponDetailResponses = couponDetailService.getCouponByMemberId(memberId);

        assertNotNull(couponDetailResponses);
        assertEquals(2, couponDetailResponses.size());

        assertEquals(couponDetail1.getCouponId(), couponDetailResponses.get(0).getCouponId());
        assertEquals(couponDetail1.getMember(), member);
        assertEquals(couponDetail1.getUseDate(), couponDetailResponses.get(0).getUseDate());
        assertEquals(couponDetail1.getUseType(), couponDetailResponses.get(0).getUseType());

        assertEquals(couponDetail2.getCouponId(), couponDetailResponses.get(1).getCouponId());
        assertEquals(couponDetail2.getMember(), member);
        assertEquals(couponDetail2.getUseDate(), couponDetailResponses.get(1).getUseDate());
        assertEquals(couponDetail2.getUseType(), couponDetailResponses.get(1).getUseType());
    }
}