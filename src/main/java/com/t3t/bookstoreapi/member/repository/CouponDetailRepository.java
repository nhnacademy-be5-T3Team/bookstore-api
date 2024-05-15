package com.t3t.bookstoreapi.member.repository;

import com.t3t.bookstoreapi.member.model.entity.CouponDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponDetailRepository extends JpaRepository<CouponDetail, String> {
    List<CouponDetail> findByMemberId(Long memberId);
}

