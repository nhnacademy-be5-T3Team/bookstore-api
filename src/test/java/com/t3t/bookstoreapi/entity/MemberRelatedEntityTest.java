package com.t3t.bookstoreapi.entity;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.t3t.bookstoreapi.member.domain.Addresses;
import com.t3t.bookstoreapi.member.domain.MemberAddress;
import com.t3t.bookstoreapi.member.repository.AddressRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@SpringBootTest
@Transactional
@ActiveProfiles("prod")
public class MemberRelatedEntityTest {

    @Autowired
    private AddressRepository addressRepository;

    @Test
    @DisplayName("MemberAddress 엔티티 맵핑 테스트")
    void memberAddressTest() {

        // given
        Addresses addresses = addressRepository.save(Addresses.builder()
                        .roadNameAddress("대전광역시 유성구 대학로 291")
                        .addressNumber(12345)
                        .build());

        // when
        Optional<Addresses> resultAddresses = addressRepository.findById(addresses.getId());

        // then
        Assertions.assertTrue(resultAddresses.isPresent());
        Assertions.assertEquals(addresses, resultAddresses.get());
    }

}
