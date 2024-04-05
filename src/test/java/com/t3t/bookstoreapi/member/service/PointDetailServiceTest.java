package com.t3t.bookstoreapi.member.service;

import com.t3t.bookstoreapi.member.domain.Member;
import com.t3t.bookstoreapi.member.domain.PointDetail;
import com.t3t.bookstoreapi.member.dto.response.PointDetailResponse;
import com.t3t.bookstoreapi.member.repository.MemberRepository;
import com.t3t.bookstoreapi.member.repository.PointDetailRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
        memberRepository.save(member);

        PointDetail pointDetail1 = pointDetailRepository.save(PointDetail.builder()
                        .member(member)
                        .content("구매 포인트 적립")
                        .pointDetailType("saved")
                        .pointDetailDate(LocalDateTime.of(2024, 3, 31, 0, 0, 0))
                        .pointAmount(new BigDecimal(1000))
                .build());
        PointDetail pointDetail2 = pointDetailRepository.save(PointDetail.builder()
                        .member(member)
                .content("구매 포인트 사용")
                .pointDetailType("used")
                .pointDetailDate(LocalDateTime.of(2024, 4, 1, 0, 0, 0))
                .pointAmount(new BigDecimal(500))
                .build());

        List<PointDetailResponse> pointDetailResponses = pointDetailService.getPointByMemberId(memberId);

        assertNotNull(pointDetailResponses);
        assertEquals(2, pointDetailResponses.size());

        assertEquals(pointDetail1.getPointDetailId(), pointDetailResponses.get(0).getPointDetailId());
        assertEquals(pointDetail1.getMember(), member);
        assertEquals(pointDetail1.getPointDetailType(), pointDetailResponses.get(0).getPointDetailType());
        assertEquals(pointDetail1.getPointDetailDate(), pointDetailResponses.get(0).getPointDetailDate());
        assertEquals(pointDetail1.getPointAmount(), pointDetailResponses.get(0).getPointAmount());

        assertEquals(pointDetail2.getPointDetailId(), pointDetailResponses.get(1).getPointDetailId());
        assertEquals(pointDetail2.getMember(), member);
        assertEquals(pointDetail2.getPointDetailType(), pointDetailResponses.get(1).getPointDetailType());
        assertEquals(pointDetail2.getPointDetailDate(), pointDetailResponses.get(1).getPointDetailDate());
        assertEquals(pointDetail2.getPointAmount(), pointDetailResponses.get(1).getPointAmount());

//        PointDetail point1 = new PointDetail(, member, "구매 포인트 적립", "saved", LocalDateTime.of(2024, 3, 31, 0, 0, 0), new BigDecimal(1000));
//        PointDetail point2 = new PointDetail(1002L, member, "구매 포인트 사용", "used", LocalDateTime.of(2024, 4, 1, 0, 0, 0), new BigDecimal(500));
//        pointDetailRepository.save(point2);

        /*assertEquals(pointDetailResponses.get(0).getContent(), "구매 포인트 적립");
        assertEquals(pointDetailResponses.get(0).getPointDetailType(), "saved");
        assertEquals(pointDetailResponses.get(0).getPointDetailDate(), LocalDateTime.of(2024, 3, 31, 0, 0, 0));
        assertEquals(pointDetailResponses.get(0).getPointAmount(), new BigDecimal(1000));

        assertEquals(pointDetailResponses.get(1).getPointDetailId(), 1002L);
        assertEquals(pointDetailResponses.get(1).getContent(), "구매 포인트 사용");
        assertEquals(pointDetailResponses.get(1).getPointDetailType(), "used");
        assertEquals(pointDetailResponses.get(1).getPointDetailDate(), LocalDateTime.of(2024, 4, 1, 0, 0, 0));
        assertEquals(pointDetailResponses.get(1).getPointAmount(), new BigDecimal(500));*/
    }
}
