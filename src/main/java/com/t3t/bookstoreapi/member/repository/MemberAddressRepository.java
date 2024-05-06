package com.t3t.bookstoreapi.member.repository;

import com.t3t.bookstoreapi.member.model.entity.MemberAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberAddressRepository extends JpaRepository<MemberAddress, Long>, MemberAddressRepositoryCustom {
    int countByMemberId(long memberId);
    List<MemberAddress> findByMemberId(long memberId);
}
