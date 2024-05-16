package com.t3t.bookstoreapi.member.repository;

import com.t3t.bookstoreapi.member.model.entity.MemberGradePolicy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MemberGradePolicyRepository extends JpaRepository<MemberGradePolicy, Long> {

    @Query("SELECT g.policy FROM MemberGrade m JOIN FETCH MemberGrade g")
    List<MemberGradePolicy> findAll();

    @Query("SELECT m FROM MemberGrade m JOIN FETCH m.policy WHERE m.policy.policyId = :policyId")
    Optional<MemberGradePolicy> findByPolicyId(Long policyId);

}
