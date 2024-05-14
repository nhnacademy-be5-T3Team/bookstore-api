package com.t3t.bookstoreapi.coupon.service;

import com.t3t.bookstoreapi.coupon.adapter.CouponAdapter;
import com.t3t.bookstoreapi.coupon.repository.CouponDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CouponDetailService {
    private final CouponDetailRepository couponDetailRepository;
    private final CouponAdapter couponAdapter;
/*
    public void saveCouponDetail(CouponDetailRequest request){

    }*/
}
