package com.t3t.bookstoreapi.pointdetail.repository;

import com.t3t.bookstoreapi.pointdetail.model.entity.PointDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PointDetailRepository extends JpaRepository<PointDetail, Long> {
    List<PointDetail> findByMemberId(Long memberId);
    boolean existsByMemberIdAndPointDetailType(Long memberId, String pointDetailType);
    PointDetail findByMemberIdAndPointDetailType(Long memberId, String pointDetailType);
    boolean existsByPointDetailType(String pointDetailType);
    PointDetail findByPointDetailType(String pointDetailType);
}