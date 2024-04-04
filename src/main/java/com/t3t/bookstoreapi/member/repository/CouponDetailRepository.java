package com.t3t.bookstoreapi.member.repository;

import com.t3t.bookstoreapi.member.domain.CouponDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponDetailRepository extends JpaRepository<CouponDetail, Long> {
    List<CouponDetail> findByMemberId(Long memberId);
}

