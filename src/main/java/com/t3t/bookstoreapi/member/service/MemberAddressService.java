package com.t3t.bookstoreapi.member.service;

import com.t3t.bookstoreapi.member.exception.MemberAddressNotFoundForIdException;
import com.t3t.bookstoreapi.member.model.dto.MemberAddressDto;
import com.t3t.bookstoreapi.member.repository.MemberAddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberAddressService {

    private final MemberAddressRepository memberAddressRepository;

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
}
