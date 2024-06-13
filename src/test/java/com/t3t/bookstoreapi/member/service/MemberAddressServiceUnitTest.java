package com.t3t.bookstoreapi.member.service;

import com.t3t.bookstoreapi.member.exception.MemberAddressCountLimitExceededException;
import com.t3t.bookstoreapi.member.exception.MemberAddressNotFoundForIdException;
import com.t3t.bookstoreapi.member.model.constant.MemberGradeType;
import com.t3t.bookstoreapi.member.model.constant.MemberRole;
import com.t3t.bookstoreapi.member.model.constant.MemberStatus;
import com.t3t.bookstoreapi.member.model.dto.MemberAddressDto;
import com.t3t.bookstoreapi.member.model.entity.Address;
import com.t3t.bookstoreapi.member.model.entity.Member;
import com.t3t.bookstoreapi.member.model.entity.MemberAddress;
import com.t3t.bookstoreapi.member.model.entity.MemberGrade;
import com.t3t.bookstoreapi.member.model.request.MemberAddressCreationRequest;
import com.t3t.bookstoreapi.member.repository.AddressRepository;
import com.t3t.bookstoreapi.member.repository.MemberAddressRepository;
import com.t3t.bookstoreapi.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("MemberAddressServiceUnitTest 단위 테스트")
class MemberAddressServiceUnitTest {

    @Mock
    private MemberAddressRepository memberAddressRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private AddressRepository addressRepository;
    @InjectMocks
    private MemberAddressService memberAddressService;


    @Nested
    @DisplayName("회원 주소 조회 테스트")
    class MemberAddressReadTest {

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

    @Nested
    @DisplayName("회원 주소 생성 테스트")
    class MemberAddressCreationTest {

        /**
         * 회원 주소 생성 테스트
         *
         * @author woody35545(구건모)
         * @see MemberAddressService#createMemberAddress(MemberAddressCreationRequest)
         */
        @Test
        @DisplayName("회원 주소 생성")
        void createMemberAddressTest() {
            // given
            final long testMemberId = 1L;

            final MemberAddressCreationRequest testMemberAddressCreationRequest = MemberAddressCreationRequest.builder()
                    .memberId(testMemberId)
                    .addressNumber(12345)
                    .addressNickname("testAddressNickname")
                    .roadNameAddress("testRoadNameAddress")
                    .addressDetail("testAddressDetail")
                    .build();

            final Member testMember = Member.builder()
                    .id(testMemberId)
                    .build();

            final Address testAddress = Address.builder()
                    .id(1L)
                    .roadNameAddress(testMemberAddressCreationRequest.getRoadNameAddress())
                    .addressNumber(testMemberAddressCreationRequest.getAddressNumber())
                    .build();

            final MemberAddress testMemberAddress = MemberAddress.builder()
                    .id(1L)
                    .member(testMember)
                    .address(testAddress)
                    .addressNickname(testMemberAddressCreationRequest.getAddressNickname())
                    .addressDetail(testMemberAddressCreationRequest.getAddressDetail())
                    .build();

            when(memberRepository.findById(any(Long.class)))
                    .thenReturn(Optional.of(testMember));

            when(memberAddressRepository.countByMemberId(testMember.getId())).thenReturn(0);

            when(addressRepository.findByRoadNameAddressAndAddressNumber(any(String.class), any(Integer.class)))
                    .thenReturn(Optional.of(testAddress));

            when(memberAddressRepository.save(any(MemberAddress.class)))
                    .thenReturn(testMemberAddress);

            // when
            MemberAddressDto resultMemberAddressDto = memberAddressService.createMemberAddress(testMemberAddressCreationRequest);

            // then
            assertEquals(MemberAddressDto.of(testMemberAddress), resultMemberAddressDto);
        }

        @Test
        @DisplayName("회원 주소 생성 - 회원 주소 등록 제한 초과")
        void createMemberAddressTestWithExceedLimit() {
            // given
            final int maxMemberAddressCount = 10;

            final long testMemberId = 1L;

            final MemberAddressCreationRequest testMemberAddressCreationRequest = MemberAddressCreationRequest.builder()
                    .memberId(testMemberId)
                    .addressNumber(12345)
                    .addressNickname("testAddressNickname")
                    .roadNameAddress("testRoadNameAddress")
                    .addressDetail("testAddressDetail")
                    .build();

            final Member testMember = Member.builder()
                    .id(testMemberId)
                    .build();

            when(memberRepository.findById(any(Long.class)))
                    .thenReturn(Optional.of(testMember));

            when(memberAddressRepository.countByMemberId(testMember.getId())).thenReturn(maxMemberAddressCount);

            // when & then
            assertThrows(MemberAddressCountLimitExceededException.class,
                    () -> memberAddressService.createMemberAddress(testMemberAddressCreationRequest));
        }
    }

    /**
     * 회원 기본주소 변경 테스트
     *
     * @author woody35545(구건모)
     * @see MemberAddressService#modifyDefaultAddress(long)
     */
    @Test
    @DisplayName("회원 주소 변경 테스트")
    void modifyDefaultAddressTest() {
        // given
        MemberGrade testMemberGrade = MemberGrade.builder()
                .name(MemberGradeType.NORMAL.toString())
                .build();

        Member testMember = Member.builder()
                .id(1L)
                .name("testName")
                .birthDate(LocalDate.of(2023, 6, 13))
                .phone("010-1234-5678")
                .email("test@mail.com")
                .grade(testMemberGrade)
                .role(MemberRole.USER)
                .status(MemberStatus.ACTIVE)
                .point(0L)
                .build();

        Address testAddress = Address.builder()
                .id(0L)
                .addressNumber(12345)
                .roadNameAddress("testRoadNameAddress")
                .build();

        MemberAddress testMemberAddress = MemberAddress.builder()
                .id(0L)
                .addressNickname("testAddressNickname")
                .addressDetail("testAddressDetail")
                .member(testMember)
                .address(testAddress)
                .isDefaultAddress(false)
                .build();

        when(memberAddressRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(testMemberAddress));
        when(memberAddressRepository.findByMemberId(testMember.getId())).thenReturn(List.of(testMemberAddress));

        // when
        memberAddressService.modifyDefaultAddress(testMemberAddress.getId());

        // then
        assertTrue(testMemberAddress.getIsDefaultAddress());
    }
}
