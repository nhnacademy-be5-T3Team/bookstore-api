package com.t3t.bookstoreapi.member;

import com.t3t.bookstoreapi.member.model.constant.MemberRole;
import com.t3t.bookstoreapi.member.model.constant.MemberStatus;
import com.t3t.bookstoreapi.member.model.entity.*;
import com.t3t.bookstoreapi.member.repository.*;
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
@ActiveProfiles("test")
class MemberRelatedEntityTest {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private MemberGradePolicyRepository memberGradePolicyRepository;

    @Autowired
    private MemberGradeRepository memberGradeRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberAddressRepository memberAddressRepository;

    /**
     * MemberAddress 엔티티 맵핑 테스트
     * @author woody33545(구건모)
     */
    @Test
    @DisplayName("Address 엔티티 맵핑 테스트")
    void addressTest() {

        // given
        Address address = addressRepository.save(Address.builder()
                        .roadNameAddress("test")
                        .addressNumber(12345)
                        .build());

        // when
        Optional<Address> resultAddresses = addressRepository.findById(address.getId());

        // then
        Assertions.assertTrue(resultAddresses.isPresent());
        Assertions.assertEquals(address, resultAddresses.get());
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

        // given
        MemberGradePolicy memberGradePolicy = memberGradePolicyRepository.save(MemberGradePolicy.builder()
                .startAmount(BigDecimal.valueOf(0))
                .endAmount(BigDecimal.valueOf(100000))
                .build());

        MemberGrade memberGrade = memberGradeRepository.save(MemberGrade.builder()
                .policy(memberGradePolicy)
                .name("test")
                .build());

        Member member = memberRepository.save(Member.builder()
                .name("test")
                .email("woody@mail.com")
                .point(1000L)
                .phone("010-1234-5678")
                .latestLogin(LocalDateTime.now())
                .birthDate(LocalDateTime.now().toLocalDate())
                .gradeId(memberGrade)
                .status(MemberStatus.ACTIVE)
                .role(MemberRole.USER)
                .build());

        // when
        Optional<Member> resultMember = memberRepository.findById(member.getId());

        // then
        Assertions.assertTrue(resultMember.isPresent());
        Assertions.assertEquals(member, resultMember.get());
    }

    /**
     * MemberAddress 엔티티 맵핑 테스트
     * @author woody33545(구건모)
     */
    @Test
    @DisplayName("MemberAddress 엔티티 맵핑 테스트")
    void memberAddressTest() {
        // given
        MemberGradePolicy memberGradePolicy = memberGradePolicyRepository.save(MemberGradePolicy.builder()
                .startAmount(BigDecimal.valueOf(0))
                .endAmount(BigDecimal.valueOf(100000))
                .build());

        MemberGrade memberGrade = memberGradeRepository.save(MemberGrade.builder()
                .policy(memberGradePolicy)
                .name("test")
                .build());

        Member member = memberRepository.save(Member.builder()
                .name("test")
                .email("woody@mail.com")
                .point(1000L)
                .phone("010-1234-5678")
                .latestLogin(LocalDateTime.now())
                .birthDate(LocalDateTime.now().toLocalDate())
                .gradeId(memberGrade)
                .status(MemberStatus.ACTIVE)
                .role(MemberRole.USER)
                .build());

        Address address = addressRepository.save(Address.builder()
                .roadNameAddress("test")
                .addressNumber(12345)
                .build());

        MemberAddress memberAddress = memberAddressRepository.save(MemberAddress.builder()
                .member(member)
                .address(address)
                .addressDetail("test")
                .addressNickname("test")
                .build());

        // when
        Optional<MemberAddress> resultMemberAddress = memberAddressRepository.findById(memberAddress.getMemberAddressId());

        // then
        Assertions.assertTrue(resultMemberAddress.isPresent());
        Assertions.assertEquals(memberAddress, resultMemberAddress.get());

    }
}
