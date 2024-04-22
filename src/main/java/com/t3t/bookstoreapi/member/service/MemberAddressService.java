package com.t3t.bookstoreapi.member.service;

import com.t3t.bookstoreapi.member.exception.MemberAddressNotFoundForIdException;
import com.t3t.bookstoreapi.member.exception.MemberNotFoundForIdException;
import com.t3t.bookstoreapi.member.model.dto.MemberAddressDto;
import com.t3t.bookstoreapi.member.repository.MemberAddressRepository;
import com.t3t.bookstoreapi.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberAddressService {

    private final MemberAddressRepository memberAddressRepository;
    private final MemberRepository memberRepository;

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
}
