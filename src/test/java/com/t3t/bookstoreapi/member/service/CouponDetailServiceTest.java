package com.t3t.bookstoreapi.member.service;

import com.t3t.bookstoreapi.member.domain.CouponDetail;
import com.t3t.bookstoreapi.member.domain.Member;
import com.t3t.bookstoreapi.member.dto.response.CouponDetailResponse;
import com.t3t.bookstoreapi.member.repository.CouponDetailRepository;
import com.t3t.bookstoreapi.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
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

        List<CouponDetail> couponDetail
                = Arrays.asList(
                new CouponDetail("1011", member, LocalDateTime.of(2024, 3, 31, 0, 0, 0), "사용"),
                new CouponDetail("1012", member, null, "미사용")
        );

        List<CouponDetailResponse> couponDetailResponses = couponDetailService.getCouponByMemberId(memberId);

        assertNotNull(couponDetailResponses);
        assertEquals(2, couponDetailResponses.size());

        assertEquals(couponDetailResponses.get(0).getCouponId(), "1011");
        assertEquals(couponDetailResponses.get(0).getUseDate(), LocalDateTime.of(2024, 3, 31, 0, 0, 0));
        assertEquals(couponDetailResponses.get(0).getUseType(), "used");

        assertEquals(couponDetailResponses.get(1).getCouponId(), "1012");
        assertEquals(couponDetailResponses.get(1).getUseDate(), null);
        assertEquals(couponDetailResponses.get(1).getUseType(), "issued");

    }
}