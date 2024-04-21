package com.t3t.bookstoreapi.member.repository;

import com.t3t.bookstoreapi.member.model.constant.MemberRole;
import com.t3t.bookstoreapi.member.model.constant.MemberStatus;
import com.t3t.bookstoreapi.member.model.dto.MemberAddressDto;
import com.t3t.bookstoreapi.member.model.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class MemberAddressRepositoryTest {

    @Autowired
    private MemberAddressRepository memberAddressRepository;

    @Autowired
    private MemberGradePolicyRepository memberGradePolicyRepository;

    @Autowired
    private MemberGradeRepository memberGradeRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AddressRepository addressRepository;

    /**
     * 회원 주소 식별자로 회원 주소 조회하는 Custom Repository(QueryDSL) 메서드 테스트
     *
     * @author woody35545(구건모)
     * @see MemberAddressRepository#getMemberAddressDtoById(long)
     */
    @Test
    @DisplayName("회원 주소 DTO 조회 - 식별자로 조회")
    void getMemberAddressDtoByIdTest() {
        // given
        MemberGradePolicy testMemberGradePolicy = memberGradePolicyRepository.save(MemberGradePolicy.builder()
                .startAmount(BigDecimal.valueOf(0))
                .endAmount(BigDecimal.valueOf(100000))
                .build());

        MemberGrade memberGrade = memberGradeRepository.save(MemberGrade.builder()
                .policy(testMemberGradePolicy)
                .name("test")
                .build());

        Member testMember = memberRepository.save(Member.builder()
                .name("test")
                .email("test@mail.com")
                .point(1000L)
                .phone("010-1234-5678")
                .latestLogin(LocalDateTime.now())
                .birthDate(LocalDateTime.now().toLocalDate())
                .grade(memberGrade)
                .status(MemberStatus.ACTIVE)
                .role(MemberRole.USER)
                .build());

        Address testAddress = addressRepository.save(Address.builder()
                .roadNameAddress("test")
                .addressNumber(12345)
                .build());

        MemberAddress testMemberAddress = memberAddressRepository.save(MemberAddress.builder()
                .member(testMember)
                .address(testAddress)
                .addressDetail("test")
                .addressNickname("test")
                .build());

        // when
        Optional<MemberAddressDto> optMemberAddressDto = memberAddressRepository.getMemberAddressDtoById(testMemberAddress.getId());


        // then
        assertTrue(optMemberAddressDto.isPresent());

        MemberAddressDto memberAddressDto = optMemberAddressDto.get();
        assertEquals(testMemberAddress.getId(), memberAddressDto.getId());
        assertEquals(testMember.getId(), memberAddressDto.getMemberId());
        assertEquals(testAddress.getAddressNumber(), memberAddressDto.getAddressNumber());
        assertEquals(testAddress.getRoadNameAddress(), memberAddressDto.getRoadNameAddress());
        assertEquals(testMemberAddress.getAddressNickname(), memberAddressDto.getAddressNickname());
        assertEquals(testMemberAddress.getAddressDetail(), memberAddressDto.getAddressDetail());

    }

}
