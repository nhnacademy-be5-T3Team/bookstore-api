package com.t3t.bookstoreapi.member.service;

import com.t3t.bookstoreapi.member.exception.MemberAddressCountLimitExceededException;
import com.t3t.bookstoreapi.member.exception.MemberAddressNotFoundForIdException;
import com.t3t.bookstoreapi.member.exception.MemberNotFoundForIdException;
import com.t3t.bookstoreapi.member.model.dto.MemberAddressDto;
import com.t3t.bookstoreapi.member.model.entity.Address;
import com.t3t.bookstoreapi.member.model.entity.Member;
import com.t3t.bookstoreapi.member.model.entity.MemberAddress;
import com.t3t.bookstoreapi.member.model.request.MemberAddressCreationRequest;
import com.t3t.bookstoreapi.member.repository.AddressRepository;
import com.t3t.bookstoreapi.member.repository.MemberAddressRepository;
import com.t3t.bookstoreapi.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.junit.Test;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberAddressService {

    private final MemberAddressRepository memberAddressRepository;
    private final MemberRepository memberRepository;
    private final AddressRepository addressRepository;
    private static final int MAX_MEMBER_ADDRESS_COUNT = 10;

    /**
     * 회원 주소 식별자로 특정 회원 주소 정보를 조회한다.
     *
     * @param memberAddressId 조회하려는 회원 주소 식별자
     * @author woody35545(구건모)
     */
    @Transactional(readOnly = true)
    public MemberAddressDto getMemberAddressById(long memberAddressId) {
        return memberAddressRepository.getMemberAddressDtoById(memberAddressId)
                .orElseThrow(() -> new MemberAddressNotFoundForIdException(memberAddressId));
    }


    /**
     * 회원 식별자로 특정 회원이 등록한 모든 회원 주소 정보들을 조회한다.
     *
     * @param memberId 주소를 조회하고자 하는 회원 식별자
     * @author woody35545(구건모)
     */
    @Transactional(readOnly = true)
    public List<MemberAddressDto> getMemberAddressListByMemberId(long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new MemberNotFoundForIdException(memberId);
        }

        return memberAddressRepository.getMemberAddressDtoListByMemberId(memberId);
    }

    /**
     * 회원 주소를 생성한다.
     *
     * @param request 회원 주소 생성 요청 정보
     * @author woody35545(구건모)
     */
    public MemberAddressDto createMemberAddress(MemberAddressCreationRequest request) {

        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new MemberNotFoundForIdException(request.getMemberId()));

        if (memberAddressRepository.countByMemberId(member.getId()) >= MAX_MEMBER_ADDRESS_COUNT) {
            throw new MemberAddressCountLimitExceededException();
        }

        Address address = addressRepository.findByRoadNameAddressAndAddressNumber(request.getRoadNameAddress(), request.getAddressNumber())
                .orElseGet(() -> addressRepository.save(Address.builder()
                        .roadNameAddress(request.getRoadNameAddress())
                        .addressNumber(request.getAddressNumber())
                        .build()));

        MemberAddress memberAddress = memberAddressRepository.save(MemberAddress.builder()
                .address(address)
                .member(member)
                .addressNickname(request.getAddressNickname())
                .addressDetail(request.getAddressDetail())
                .isDefaultAddress(false)
                .build());

        return MemberAddressDto.of(memberAddress);
    }


    /**
     * 기본 주소 설정 및 변경
     *
     * @param memberAddressId 기본 주소로 설정할 회원 주소 식별자
     * @author woody35545(구건모)
     */
    public void modifyDefaultAddress(long memberAddressId) {
        MemberAddress memberAddress = memberAddressRepository.findById(memberAddressId)
                .orElseThrow(() -> new MemberAddressNotFoundForIdException(memberAddressId));

        if (memberAddress.getIsDefaultAddress()) {
            return;
        }

        memberAddressRepository.findByMemberId(memberAddress.getMember().getId()).stream()
                .forEach(address -> address.asNonDefaultAddress());

        memberAddress.asDefaultAddress();
    }

    /**
     * 회원 주소 삭제
     *
     * @param memberAddressId 삭제할 회원 주소 식별자
     * @author woody35545(구건모)
     */
    public void deleteMemberAddress(long memberAddressId) {
        MemberAddress memberAddress = memberAddressRepository.findById(memberAddressId)
                .orElseThrow(() -> new MemberAddressNotFoundForIdException(memberAddressId));

        long addressId = memberAddress.getAddress().getId();

        memberAddressRepository.delete(memberAddress);

        if (!memberAddressRepository.existsByAddressId(addressId)) {
            addressRepository.delete(memberAddress.getAddress());
        }
    }

    /**
     * 주소 별칭 변경
     * author woody35545(구건모)
     */
    public void modifyAddressNickname(long memberAddressId, String addressNickname) {
        MemberAddress memberAddress = memberAddressRepository.findById(memberAddressId)
                .orElseThrow(() -> new MemberAddressNotFoundForIdException(memberAddressId));

        memberAddress.modifyNickname(addressNickname);
    }
}
