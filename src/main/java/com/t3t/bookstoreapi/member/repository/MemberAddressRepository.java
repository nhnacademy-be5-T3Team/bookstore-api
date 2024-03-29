package com.t3t.bookstoreapi.member.repository;

import com.t3t.bookstoreapi.member.domain.MemberAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberAddressRepository extends JpaRepository<MemberAddress, Long>{
}
