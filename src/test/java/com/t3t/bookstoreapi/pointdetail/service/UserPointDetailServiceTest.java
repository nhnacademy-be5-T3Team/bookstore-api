package com.t3t.bookstoreapi.pointdetail.service;

import com.t3t.bookstoreapi.member.model.constant.MemberRole;
import com.t3t.bookstoreapi.member.model.constant.MemberStatus;
import com.t3t.bookstoreapi.member.model.entity.Member;
import com.t3t.bookstoreapi.pointdetail.model.entity.PointDetail;
import com.t3t.bookstoreapi.pointdetail.model.response.PointDetailResponse;
import com.t3t.bookstoreapi.pointdetail.repository.PointDetailRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// ㅇㅏ직 안 돌아간다.. 고쳐야 한ㄷㅏ..
@ExtendWith(MockitoExtension.class)
class UserPointDetailServiceTest {

    @Mock
    private PointDetailRepository pointDetailRepository;

    @InjectMocks
    private UserPointDetailService userPointDetailService;

    @Test
    void getPointDetailList() {
        // given
        Long memberId = 1L;
        Member member = Member.builder()
                .id(memberId)
                .name("홍길동")
                .phone("010-1234-5678")
                .email("hong@naver.com")
                .birthDate(LocalDate.of(2002, 1, 1))
                .latestLogin(LocalDateTime.of(2024,1, 1, 0, 0, 0, 0))
                .point(0L)
                .status(MemberStatus.ACTIVE)
                .role(MemberRole.USER)
                .build();

        List<PointDetail> testPointDetails = new ArrayList<>();
        testPointDetails.add(PointDetail.builder()
                .pointDetailId(1L)
                .member(member)
                .content("리뷰 적립")
                .pointDetailType("saved")
                .pointDetailDate(LocalDateTime.of(2024, 1, 2, 0, 0, 0, 0))
                .pointAmount(BigDecimal.valueOf(100))
                .build());
        testPointDetails.add(PointDetail.builder()
                .pointDetailId(2L)
                .member(member)
                .content("주문 사용")
                .pointDetailType("used")
                .pointDetailDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0))
                .pointAmount(BigDecimal.valueOf(80))
                .build()
        );

        Mockito.doReturn(testPointDetails).when(pointDetailRepository).findAll();

        // when
        List<PointDetailResponse> resultPointDetailList = userPointDetailService.getPointDetailList(memberId);

        // then
        Assertions.assertEquals(testPointDetails.size(), resultPointDetailList.size());

//        // when
//        when(pointDetailRepository.findByMemberId(memberId)).thenReturn(testPointDetails.stream().map(Optional::of).collect(Collectors.toList()));

        // then
//        List<PointDetailResponse> resultPointDetailList = userPointDetailService.getPointDetailList(memberId);
        Assertions.assertEquals(testPointDetails.size(), resultPointDetailList.size());

        for (int i = 0; i < testPointDetails.size(); i++) {
            PointDetailResponse expected = PointDetailResponse.of(testPointDetails.get(i));
            PointDetailResponse actual = resultPointDetailList.get(i);

            Assertions.assertEquals(expected.getContent(), actual.getContent());
            Assertions.assertEquals(expected.getPointDetailType(), actual.getPointDetailType());
            Assertions.assertEquals(expected.getPointDetailDate(), actual.getPointDetailDate());
            Assertions.assertEquals(expected.getPointAmount(), actual.getPointAmount());
        }
    }

    @Test
    void getPointDetailByPointDetailType() {
        // given
        Long memberId = 1L;
        Member member = Member.builder()
                .id(memberId)
                .name("홍길동")
                .phone("010-1234-5678")
                .email("hong@naver.com")
                .birthDate(LocalDate.of(2002, 1, 1))
                .latestLogin(LocalDateTime.of(2024,1, 1, 0, 0, 0, 0))
                .point(0L)
                .status(MemberStatus.ACTIVE)
                .role(MemberRole.USER)
                .build();

        List<PointDetail> testPointDetails = new ArrayList<>();
        testPointDetails.add(PointDetail.builder()
                .pointDetailId(1L)
                .member(member)
                .content("리뷰 적립")
                .pointDetailType("saved")
                .pointDetailDate(LocalDateTime.of(2024, 1, 2, 0, 0, 0, 0))
                .pointAmount(BigDecimal.valueOf(100))
                .build());
        testPointDetails.add(PointDetail.builder()
                .pointDetailId(2L)
                .member(member)
                .content("주문 사용")
                .pointDetailType("used")
                .pointDetailDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0))
                .pointAmount(BigDecimal.valueOf(80))
                .build()
        );

        Mockito.when(pointDetailRepository.findByMemberIdAndPointDetailType(memberId, "saved"))
                .thenReturn(testPointDetails.stream().map(Optional::of).collect(Collectors.toList()));

        // when
        List<PointDetailResponse> resultPointDetailList = userPointDetailService.getPointDetailByPointDetailType(memberId, "saved");

        // then
        Assertions.assertEquals(1, resultPointDetailList.size());
        PointDetailResponse resultPointDetail = resultPointDetailList.get(0);
        PointDetail expectedPointDetail = testPointDetails.get(1);

        Assertions.assertEquals(expectedPointDetail.getContent(), resultPointDetail.getContent());
        Assertions.assertEquals(expectedPointDetail.getPointDetailType(), resultPointDetail.getPointDetailType());
        Assertions.assertEquals(expectedPointDetail.getPointDetailDate(), resultPointDetail.getPointDetailDate());
        Assertions.assertEquals(expectedPointDetail.getPointAmount(), resultPointDetail.getPointAmount());
    }
}