package com.t3t.bookstoreapi.member.repository;

import com.t3t.bookstoreapi.member.model.entity.PointDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PointDetailRepository extends JpaRepository<PointDetail, Long> {
    List<PointDetail> findByMemberId(Long memberId);
}