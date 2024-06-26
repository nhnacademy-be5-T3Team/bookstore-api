//package com.t3t.bookstoreapi.member.service;
//
//import com.t3t.bookstoreapi.member.model.entity.Member;
//import com.t3t.bookstoreapi.pointdetail.model.entity.PointDetail;
//import com.t3t.bookstoreapi.pointdetail.model.response.PointDetailResponse;
//import com.t3t.bookstoreapi.member.repository.MemberRepository;
//import com.t3t.bookstoreapi.pointdetail.repository.PointDetailRepository;
//import com.t3t.bookstoreapi.pointdetail.service.UserPointDetailService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@Transactional
////@ActiveProfiles("dev")
//public class PointDetailServiceTest {
//
//    @Autowired
//    private MemberRepository memberRepository;
//
//    @Autowired
//    private PointDetailRepository pointDetailRepository;
//
//    @Autowired
//    private UserPointDetailService pointDetailService;
//
//    @Test
//    void getPointByPointDetailType() {
//        Long memberId = 1L;
//        Member member = memberRepository.findById(memberId).orElse(null);
//        memberRepository.save(member);
//
//        PointDetail pointDetail1 = pointDetailRepository.save(PointDetail.builder()
//                        .member(member)
//                        .content("구매 포인트 적립")
//                        .pointDetailType("saved")
//                        .pointDetailDate(LocalDateTime.of(2024, 3, 31, 0, 0, 0))
//                        .pointAmount(new BigDecimal(1000))
//                .build());
//        PointDetail pointDetail2 = pointDetailRepository.save(PointDetail.builder()
//                        .member(member)
//                .content("구매 포인트 사용")
//                .pointDetailType("used")
//                .pointDetailDate(LocalDateTime.of(2024, 4, 1, 0, 0, 0))
//                .pointAmount(new BigDecimal(500))
//                .build());
//
//        List<PointDetailResponse> pointDetailResponses = pointDetailService.getPointDetailByPointDetailType(pointDetail1.getPointDetailType());
//
//        assertNotNull(pointDetailResponses);
//        assertEquals(2, pointDetailResponses.size());
//
//        assertEquals(pointDetail1.getMember(), member);
//        assertEquals(pointDetail1.getPointDetailType(), pointDetailResponses.get(0).getPointDetailType());
//        assertEquals(pointDetail1.getPointDetailDate(), pointDetailResponses.get(0).getPointDetailDate());
//        assertEquals(pointDetail1.getPointAmount(), pointDetailResponses.get(0).getPointAmount());
//
//        assertEquals(pointDetail2.getMember(), member);
//        assertEquals(pointDetail2.getPointDetailType(), pointDetailResponses.get(1).getPointDetailType());
//        assertEquals(pointDetail2.getPointDetailDate(), pointDetailResponses.get(1).getPointDetailDate());
//        assertEquals(pointDetail2.getPointAmount(), pointDetailResponses.get(1).getPointAmount());
//    }
//}
