package com.t3t.bookstoreapi.member.repository;

import com.t3t.bookstoreapi.member.model.entity.MemberGradePolicy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberGradePolicyRepository extends JpaRepository<MemberGradePolicy, Long> {
}
