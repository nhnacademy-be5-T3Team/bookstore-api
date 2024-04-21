package com.t3t.bookstoreapi.member.repository;

import com.t3t.bookstoreapi.member.model.dto.MemberAddressDto;

import java.util.List;
import java.util.Optional;

/**
 * MemberAddress 엔티티에 대한 커스텀 쿼리를 정의하기 위한 인터페이스
 *
 * @author woody35545(구건모)
 */
public interface MemberAddressRepositoryCustom {
    /**
     * 회원 주소 식별자로 회원 주소 정보를 조회한다.<br>
     *
     * @param memberAddressId 조회하려는 회원 주소 식별자
     * @return 회원 주소에 관한 정보를 담은 DTO
     * @author woody35545(구건모)
     */
    Optional<MemberAddressDto> getMemberAddressDtoById(long memberAddressId);

    /**
     * 회원 식별자로 등록된 모든 회원 주소 정보를 조회한다.<br>
     *
     * @param memberId 조회하려는 회원의 식별자
     * @return 회원 주소에 관한 정보를 담은 DTO 리스트
     * @author woody35545(구건모)
     */
    List<MemberAddressDto> getMemberAddressDtoListByMemberId(long memberId);
}
