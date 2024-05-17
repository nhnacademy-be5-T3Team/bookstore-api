package com.t3t.bookstoreapi.member.repository;

import com.t3t.bookstoreapi.member.model.entity.MemberGrade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberGradeRepository extends JpaRepository<MemberGrade, Integer> {
    Optional<MemberGrade> findByName(String name);
}