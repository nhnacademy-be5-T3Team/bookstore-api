package com.t3t.bookstoreapi.pointdetail.repository;

import com.t3t.bookstoreapi.pointdetail.model.entity.PointDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PointDetailRepository extends JpaRepository<PointDetail, Long> {
    List<Optional<PointDetail>> findByMemberId(Long memberId);
    boolean existsByMemberIdAndPointDetailType(Long memberId, String pointDetailType);
    Optional<PointDetail> findByMemberIdAndPointDetailType(Long memberId, String pointDetailType);
    boolean existsByPointDetailType(String pointDetailType);
    Optional<PointDetail> findByPointDetailType(String pointDetailType);
}