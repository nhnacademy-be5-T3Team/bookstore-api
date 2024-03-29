package com.t3t.bookstoreapi.entity;

import com.t3t.bookstoreapi.member.domain.Addresses;
import com.t3t.bookstoreapi.member.domain.MemberGradePolicy;
import com.t3t.bookstoreapi.member.repository.AddressRepository;
import com.t3t.bookstoreapi.member.repository.MemberGradePolicyRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
}
