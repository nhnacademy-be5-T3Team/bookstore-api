package com.t3t.bookstoreapi.member.service;

import com.t3t.bookstoreapi.member.exception.MemberAddressNotFoundForIdException;
import com.t3t.bookstoreapi.member.model.dto.MemberAddressDto;
import com.t3t.bookstoreapi.member.repository.MemberAddressRepository;
import com.t3t.bookstoreapi.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("MemberAddressServiceUnitTest 단위 테스트")
class MemberAddressServiceUnitTest {

    @Mock
    private MemberAddressRepository memberAddressRepository;
    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberAddressService memberAddressService;

    /**
     * 회원 주소 식별자로 회원 주소 조회 테스트<br>
     * 유효한 주소 식별자로 조회하는 경우, 해당 주소 정보를 반환하는지 테스트
     *
     * @author woody35545(구건모)
     * @see MemberAddressService#getMemberAddressById(long)
     */
    @Test
    @DisplayName("회원 주소 조회 - 주소 식별자로 조회")
    void getMemberAddressByIdTest() {
        // given
        final MemberAddressDto testMemberAddressDto = MemberAddressDto.builder()
                .id(1L)
                .memberId(1L)
                .addressNumber(12345)
                .addressNickname("testAddressNickname")
                .roadNameAddress("testRoadNameAddress")
                .addressDetail("testAddressDetail")
                .build();

        when(memberAddressRepository.getMemberAddressDtoById(testMemberAddressDto.getId()))
                .thenReturn(java.util.Optional.of(testMemberAddressDto));

        // when
        MemberAddressDto resultMemberAddressDto = memberAddressService.getMemberAddressById(testMemberAddressDto.getId());

        // then
        assertEquals(testMemberAddressDto, resultMemberAddressDto);
    }

    /**
     * 회원 주소 식별자로 회원 주소 조회 테스트<br>
     * 존재하지 않는 회원 주소 식별자로 조회하는 경우, MemberAddressNotFoundForIdException 가 발생하는지 테스트
     *
     * @author woody35545(구건모)
     * @see MemberAddressService#getMemberAddressById(long)
     * @see MemberAddressNotFoundForIdException
     */
    @Test
    @DisplayName("회원 주소 조회 - 주소 식별자로 조회(존재하지 않는 회원 주소)")
    void getMemberAddressByIdTestWithNotExistsId() {
        // given
        final long memberAddressId = 1L;

        when(memberAddressRepository.getMemberAddressDtoById(memberAddressId))
                .thenReturn(Optional.empty());

        // when & then
        assertThrows(MemberAddressNotFoundForIdException.class,
                () -> memberAddressService.getMemberAddressById(memberAddressId));
    }

    /**
     * 회원 식별자로 회원 주소 목록 조회 테스트<br>
     * 유효한 회원 식별자로 조회하는 경우, 해당 회원의 회원 주소 리스트를 반환하는지 테스트
     *
     * @author woody35545(구건모)
     * @see MemberAddressService#getMemberAddressListByMemberId(long)
     */
    @Test
    @DisplayName("회원 주소 목록 조회 - 회원 식별자로 조회")
    void getMemberAddressListByMemberIdTest() {
        // given
        final long testMemberId = 1L;

        final List<MemberAddressDto> testMemberAddressDtoList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            testMemberAddressDtoList.add(MemberAddressDto.builder()
                    .id((long) i)
                    .memberId(testMemberId)
                    .addressNumber(i)
                    .addressNickname("testAddressNickname" + i)
                    .roadNameAddress("testRoadNameAddress" + i)
                    .addressDetail("testAddressDetail" + i)
                    .build());
        }

        when(memberRepository.existsById(testMemberId)).thenReturn(true);

        when(memberAddressRepository.getMemberAddressDtoListByMemberId(testMemberId))
                .thenReturn(testMemberAddressDtoList);

        // when
        List<MemberAddressDto> resultMemberAddressDtoList =
                memberAddressService.getMemberAddressListByMemberId(testMemberId);

        // then
        assertEquals(testMemberAddressDtoList.size(), resultMemberAddressDtoList.size());

        for (int i = 0; i < testMemberAddressDtoList.size(); i++) {
            assertEquals(testMemberAddressDtoList.get(i), resultMemberAddressDtoList.get(i));
        }
    }
}
