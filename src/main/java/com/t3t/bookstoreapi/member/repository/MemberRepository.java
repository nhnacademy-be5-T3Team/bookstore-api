package com.t3t.bookstoreapi.member.repository;

import com.t3t.bookstoreapi.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByName(String name);
}
