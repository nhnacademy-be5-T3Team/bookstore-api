package com.t3t.bookstoreapi.member.repository;

import com.t3t.bookstoreapi.member.domain.MemberGrade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberGradeRepository extends JpaRepository<MemberGrade, Integer> {
}
