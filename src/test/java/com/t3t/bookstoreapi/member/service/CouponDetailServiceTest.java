package com.t3t.bookstoreapi.member.service;

import com.t3t.bookstoreapi.member.dto.response.CouponDetailResponse;
import com.t3t.bookstoreapi.member.exception.MemberNotFoundException;
import com.t3t.bookstoreapi.member.repository.CouponDetailRepository;
import com.t3t.bookstoreapi.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Transactional
public class CouponDetailServiceTest {

    @Mock
    private CouponDetailRepository couponDetailRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private CouponDetailService couponDetailService;

    @Test
    void getCouponByMemberId() {
        // Given
        Long memberId1 = 1L;
//        String couponId1 = UUID.randomUUID().toString().replace("-","");
        String couponId1 = "123e4567e89b12d3a456426614174001";
        LocalDateTime useDate1 = LocalDateTime.of(2021, 1, 1, 0, 0, 0);
        String useType1 = "issued";

        when(memberRepository.existsById(memberId1)).thenReturn(true);
        when(couponDetailRepository.responseFindByMemberId(memberId1)).thenReturn(List.of(
                CouponDetailResponse.builder()
                        .couponId(couponId1)
                        .useDate(useDate1)
                        .useType(useType1)
                        .build()
        ));

//        String couponId2 = UUID.randomUUID().toString().replace("-","");
        String couponId2 = "123e4567e89b12d3a456426614174000";
        LocalDateTime useDate2 = LocalDateTime.of(2021, 1, 1, 0, 0, 0);
        String useType2 = "used";

        when(memberRepository.existsById(memberId1)).thenReturn(true);
        when(couponDetailRepository.responseFindByMemberId(memberId1)).thenReturn(List.of(
                CouponDetailResponse.builder()
                        .couponId(couponId2)
                        .useDate(useDate2)
                        .useType(useType2)
                        .build()
        ));

        // When
        List<CouponDetailResponse> result1 = couponDetailService.getCouponByMemberId(memberId1);

        // Then
        assertEquals(2, result1.size());

        assertEquals(couponId1, result1.get(0).getCouponId());
        assertEquals(useDate1, result1.get(0).getUseDate());
        assertEquals(useType1, result1.get(0).getUseType());

        assertEquals(couponId2, result1.get(1).getCouponId());
        assertEquals(useDate2, result1.get(1).getUseDate());
        assertEquals(useType2, result1.get(1).getUseType());
    }

//    void whenMemberNotFound_thenThrowException() {
//        Long invalidMemberId = 999L; // 존재하지 않는 회원 ID
//        when(memberRepository.existsById(invalidMemberId)).thenReturn(false); // Mock 설정
//
//        assertThrows(MemberNotFoundException.class, () -> {
//            couponDetailService.getCouponByMemberId(invalidMemberId);
//        });
//    }
}
