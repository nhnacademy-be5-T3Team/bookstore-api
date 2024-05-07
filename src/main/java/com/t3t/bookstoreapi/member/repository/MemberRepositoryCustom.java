package com.t3t.bookstoreapi.member.repository;

import com.t3t.bookstoreapi.member.model.response.MemberInfoResponse;

import java.util.Optional;

/**
 * Member 엔티티에 대한 커스텀 쿼리를 정의하기 위한 인터페이스
 * @author woody35545(구건모)
 */
public interface MemberRepositoryCustom {
    /**
     * 회원 식별자로 회원 정보를 조회한다.
     * @param memberId 조회하려는 회원의 식별자
     * @return 조회된 회원 정보
     */
    Optional<MemberInfoResponse> getMemberInfoResponseByMemberId(long memberId);
}
