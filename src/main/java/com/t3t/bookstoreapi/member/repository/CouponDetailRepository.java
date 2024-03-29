package com.t3t.bookstoreapi.member.repository;

import com.t3t.bookstoreapi.member.dto.response.CouponDetailResponse;
import com.t3t.bookstoreapi.member.entity.CouponDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CouponDetailRepository extends JpaRepository<CouponDetail, Long> {
    List<CouponDetail> findByMemberId(Long memberId);
    @Query("SELECT c FROM CouponDetail c WHERE c.memberId = :memberId")
    List<CouponDetailResponse> responseFindByMemberId(Long memberId);
}
