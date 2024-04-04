package com.t3t.bookstoreapi.member.service;

import com.t3t.bookstoreapi.member.domain.Member;
import com.t3t.bookstoreapi.member.domain.PointDetail;
import com.t3t.bookstoreapi.member.repository.MemberRepository;
import com.t3t.bookstoreapi.member.repository.PointDetailRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("prod")
public class PointDetailServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PointDetailRepository pointDetailRepository;

    @Autowired
    private PointDetailService pointDetailService;

    @Test
    void getPointByMemberId() {
        Long memberId = 1L;
        Member member = memberRepository.findById(memberId).orElse(null);
        List<PointDetail> pointDetailResponses
                = Arrays.asList(
                        new PointDetail(1001L, member, "구매 포인트 적립", "issued", LocalDateTime.of(2024, 3, 31, 0, 0, 0), new BigDecimal(1000)),
                        new PointDetail(1002L, member, "구매 포인트 사용", "used", LocalDate.of(2024, 4, 1).atStartOfDay(), new BigDecimal(500)));

        assertNotNull(pointDetailResponses);
        assertEquals(2, pointDetailResponses.size());
        assertEquals(pointDetailResponses.get(0).getContent(), pointDetailResponses.get(0).getContent());
        assertEquals(pointDetailResponses.get(1).getContent(), pointDetailResponses.get(1).getContent());

    }

}
