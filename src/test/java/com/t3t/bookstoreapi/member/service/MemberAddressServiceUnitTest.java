package com.t3t.bookstoreapi.member.service;

import com.t3t.bookstoreapi.member.exception.MemberAddressNotFoundForIdException;
import com.t3t.bookstoreapi.member.model.dto.MemberAddressDto;
import com.t3t.bookstoreapi.member.repository.MemberAddressRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@DisplayName("MemberAddressServiceUnitTest 단위 테스트")
class MemberAddressServiceUnitTest {

    @Mock
    private MemberAddressRepository memberAddressRepository;

    @InjectMocks
    private MemberAddressService memberAddressService;

    /**
     * 회원 주소 식별자로 회원 주소 조회 테스트<br>
     * 유효한 주소 식별자로 조회하는 경우, 해당 주소 정보를 반환하는지 테스트
     * @see MemberAddressService#getMemberAddressById(long)
     * @author woody35545(구건모)
     */
    @Test
    @DisplayName("회원 주소 조회 - 주소 식별자로 조회")
    void getMemberAddressByIdTest() {
        // given
        MemberAddressDto memberAddressDto = MemberAddressDto.builder()
                .id(1L)
                .memberId(1L)
                .addressNumber(12345)
                .addressNickname("집")
                .roadNameAddress("유성구 궁동로 abc 아파트")
                .addressDetail("101동 102호")
                .build();

        Mockito.when(memberAddressRepository.getMemberAddressDtoById(memberAddressDto.getId()))
                .thenReturn(java.util.Optional.of(memberAddressDto));

        // when
        MemberAddressDto resultMemberAddressDto = memberAddressService.getMemberAddressById(memberAddressDto.getId());

        // then
        assertEquals(memberAddressDto, resultMemberAddressDto);
    }

    /**
     * 회원 주소 식별자로 회원 주소 조회 테스트<br>
     * 존재하지 않는 회원 주소 식별자로 조회하는 경우, MemberAddressNotFoundForIdException 가 발생하는지 테스트
     * @see MemberAddressService#getMemberAddressById(long)
     * @see MemberAddressNotFoundForIdException
     * @author woody35545(구건모)
     */
    @Test
    @DisplayName("회원 주소 조회 - 주소 식별자로 조회(존재하지 않는 회원 주소)")
    void getMemberAddressByIdTestWithNotExistsId() {
        // given
        long memberAddressId = 1L;

        Mockito.when(memberAddressRepository.getMemberAddressDtoById(memberAddressId))
                .thenReturn(Optional.empty());

        // when & then
        assertThrows(MemberAddressNotFoundForIdException.class,
                () -> memberAddressService.getMemberAddressById(memberAddressId));
    }
}
