package com.t3t.bookstoreapi.coupon.repository;

import com.t3t.bookstoreapi.coupon.model.entity.CouponDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponDetailRepository extends JpaRepository<CouponDetail, String> {
}
