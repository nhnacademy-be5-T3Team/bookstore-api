package com.t3t.bookstoreapi.member.repository;

import com.t3t.bookstoreapi.member.domain.PointDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface PointDetailRepository extends JpaRepository<PointDetail, Long> {
    List<PointDetail> findByMemberId(Long memberId);
}