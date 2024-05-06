package com.t3t.bookstoreapi.member.repository;

import com.t3t.bookstoreapi.member.model.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRoleRepository extends JpaRepository<Member, Long> {
    @Query("SELECT m FROM Member m WHERE m.id = :memberId and m.role = 'admin'")
    boolean checkRoleAdmin(Long memberId);
}
