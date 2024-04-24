package com.t3t.bookstoreapi.member.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.t3t.bookstoreapi.member.model.dto.MemberAddressDto;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.t3t.bookstoreapi.member.model.entity.QMemberAddress.memberAddress;

/**
 * QueryDSL 을 사용하는 MemberAddressRepositoryCustom 구현체<br>
 * MemberAddress 엔티티와 관련된 커스텀 쿼리 메소드를 정의한다.
 *
 * @author woody35545(구건모)
 */
@RequiredArgsConstructor
public class MemberAddressRepositoryCustomImpl implements MemberAddressRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    /**
     * {@inheritDoc}
     *
     * @author woody35545(구건모)
     */
    @Override
    public Optional<MemberAddressDto> getMemberAddressDtoById(long memberAddressId) {
        return Optional.ofNullable(queryFactory.select(
                        Projections.bean(MemberAddressDto.class,
                                memberAddress.id,
                                memberAddress.member.id.as("memberId"),
                                memberAddress.address.addressNumber,
                                memberAddress.address.roadNameAddress,
                                memberAddress.addressNickname,
                                memberAddress.addressDetail))
                .from(memberAddress)
                .where(memberAddress.id.eq(memberAddressId))
                .fetchOne());
    }

    /**
     * {@inheritDoc}
     * @author woody35545(구건모)
     */
    @Override
    public List<MemberAddressDto> getMemberAddressDtoListByMemberId(long memberId) {
        return queryFactory.select(Projections.bean(
                        MemberAddressDto.class,
                        memberAddress.id,
                        memberAddress.member.id.as("memberId"),
                        memberAddress.address.addressNumber,
                        memberAddress.address.roadNameAddress,
                        memberAddress.addressDetail,
                        memberAddress.addressNickname))
                .from(memberAddress)
                .where(memberAddress.member.id.eq(memberId))
                .fetch();
    }
}
