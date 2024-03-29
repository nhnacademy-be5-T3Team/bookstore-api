package com.t3t.bookstoreapi.entity;

import com.t3t.bookstoreapi.member.domain.Addresses;
import com.t3t.bookstoreapi.member.domain.Member;
import com.t3t.bookstoreapi.member.domain.MemberGrade;
import com.t3t.bookstoreapi.member.domain.MemberGradePolicy;
import com.t3t.bookstoreapi.member.repository.AddressRepository;
import com.t3t.bookstoreapi.member.repository.MemberGradePolicyRepository;
import com.t3t.bookstoreapi.member.repository.MemberGradeRepository;
import com.t3t.bookstoreapi.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@SpringBootTest
@Transactional
@ActiveProfiles("prod")
class MemberRelatedEntityTest {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private MemberGradePolicyRepository memberGradePolicyRepository;

    @Autowired
    private MemberGradeRepository memberGradeRepository;

    @Autowired
    private MemberRepository memberRepository;

    /**
     * MemberAddress 엔티티 맵핑 테스트
     * @author woody33545(구건모)
     */
    @Test
    @DisplayName("MemberAddress 엔티티 맵핑 테스트")
    void memberAddressTest() {

        // given
        Addresses addresses = addressRepository.save(Addresses.builder()
                        .roadNameAddress("대전광역시 유성구")
                        .addressNumber(12345)
                        .build());

        // when
        Optional<Addresses> resultAddresses = addressRepository.findById(addresses.getId());

        // then
        Assertions.assertTrue(resultAddresses.isPresent());
        Assertions.assertEquals(addresses, resultAddresses.get());
    }

    /**
     * MemberGradePolicy 엔티티 맵핑 테스트
     * @author woody33545(구건모)
     */
    @Test
    @DisplayName("MemberGradePolicy 엔티티 맵핑 테스트")
    void memberGradePolicyTest() {

        // given
        MemberGradePolicy memberGradePolicy = memberGradePolicyRepository.save(MemberGradePolicy.builder()
                .startAmount(BigDecimal.valueOf(0))
                .endAmount(BigDecimal.valueOf(100000))
                .build());
        // when
        Optional<MemberGradePolicy> resultMemberGradePolicy = memberGradePolicyRepository.findById(memberGradePolicy.getPolicyId());

        // then
        Assertions.assertTrue(resultMemberGradePolicy.isPresent());
        Assertions.assertEquals(memberGradePolicy, resultMemberGradePolicy.get());
    }

    /**
     * MemberGrade 엔티티 맵핑 테스트
     * @author woody33545(구건모)
     */

    @Test
    @DisplayName("MemberGrade 엔티티 맵핑 테스트")
    void memberGradeTest() {

        // given
        MemberGradePolicy memberGradePolicy = memberGradePolicyRepository.save(MemberGradePolicy.builder()
                .startAmount(BigDecimal.valueOf(0))
                .endAmount(BigDecimal.valueOf(100000))
                .build());

        MemberGrade memberGrade = memberGradeRepository.save(MemberGrade.builder()
                .policy(memberGradePolicy)
                .name("test")
                .build());

        // when
        Optional<MemberGrade> resultMemberGrade = memberGradeRepository.findById(memberGrade.getGradeId());

        // then
        Assertions.assertTrue(resultMemberGrade.isPresent());
        Assertions.assertEquals(memberGrade, resultMemberGrade.get());
    }

    /**
     * Member 엔티티 맵핑 테스트
     * @author woody33545(구건모)
     */
    @Test
    @DisplayName("Member 엔티티 맵핑 테스트")
    void memberTest() {

        MemberGradePolicy memberGradePolicy = memberGradePolicyRepository.save(MemberGradePolicy.builder()
                .startAmount(BigDecimal.valueOf(0))
                .endAmount(BigDecimal.valueOf(100000))
                .build());

        MemberGrade memberGrade = memberGradeRepository.save(MemberGrade.builder()
                .policy(memberGradePolicy)
                .name("test")
                .build());

        // given
        Member member = memberRepository.save(Member.builder()
                .name("test")
                .email("woody@mail.com")
                .point(1000L)
                .phone("010-1234-5678")
                .latestLogin(LocalDateTime.now())
                .birthDate(LocalDateTime.now().toLocalDate())
                .gradeId(memberGrade)
                .status("ACTIVE")
                .role(1)
                .build());

        // when
        Optional<Member> resultMember = memberRepository.findById(member.getId());

        // then
        Assertions.assertTrue(resultMember.isPresent());
        Assertions.assertEquals(member, resultMember.get());
    }
}
