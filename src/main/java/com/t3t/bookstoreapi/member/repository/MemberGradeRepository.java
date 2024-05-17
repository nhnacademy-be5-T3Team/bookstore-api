package com.t3t.bookstoreapi.member.repository;

import com.t3t.bookstoreapi.member.model.entity.MemberGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MemberGradeRepository extends JpaRepository<MemberGrade, Integer> {
    Optional<MemberGrade> findByName(String name);
    @Query("SELECT m.policy FROM MemberGrade m JOIN FETCH MemberGradePolicy mg WHERE m.policy.policyId = mg.policyId")
    List<MemberGrade> findAll();

    @Query("SELECT m.policy FROM MemberGrade m JOIN FETCH MemberGradePolicy mg WHERE m.policy.policyId = mg.policyId")
    Optional<MemberGrade> findByPolicyId(Long policyId);

}