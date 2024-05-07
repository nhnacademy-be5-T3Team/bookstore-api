package com.t3t.bookstoreapi.member.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.t3t.bookstoreapi.member.model.response.MemberInfoResponse;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.t3t.bookstoreapi.member.model.entity.QAccount.account;
import static com.t3t.bookstoreapi.member.model.entity.QMember.member;

@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    /**
     * 회원 식별자로 회원의 정보를 조회한다.
     *
     * @param memberId 조회하려는 회원의 식별자
     * @author woody35545
     * @see MemberInfoResponse
     */
    @Override
    public Optional<MemberInfoResponse> getMemberInfoResponseByMemberId(long memberId) {
        return Optional.ofNullable(
                queryFactory.select(Projections.bean(MemberInfoResponse.class,
                                member.id.as("memberId"),
                                member.name.as("name"),
                                member.phone.as("phone"),
                                member.email.as("email"),
                                member.birthDate.as("birthDate"),
                                member.latestLogin.as("latestLogin"),
                                member.point.as("point"),
                                member.grade.gradeId.as("gradeId"),
                                member.grade.name.as("gradeName"),
                                member.status.as("status"),
                                member.role.as("role"),
                                account.id.as("accountId")))
                        .from(member)
                        .join(account).on(member.eq(account.member))
                        .where(member.id.eq(memberId))
                        .fetchOne());
    }
}
