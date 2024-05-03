package com.t3t.bookstoreapi.pointdetail.repository;

import com.t3t.bookstoreapi.pointdetail.model.entity.PointDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PointDetailRepository extends JpaRepository<PointDetail, Long> {
    List<PointDetail> findByMemberId(Long memberId);
    PointDetail findByMemberIdAndPointDetailId(Long memberId, Long pointDetailId);
}