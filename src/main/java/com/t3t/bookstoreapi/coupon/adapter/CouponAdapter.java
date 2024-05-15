package com.t3t.bookstoreapi.coupon.adapter;

import com.t3t.bookstoreapi.coupon.client.CouponApiClient;
import com.t3t.bookstoreapi.coupon.exception.CouponApiClientException;
import com.t3t.bookstoreapi.coupon.model.response.CouponResponse;
import com.t3t.bookstoreapi.model.response.BaseResponse;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CouponAdapter {
    private final CouponApiClient couponApiClient;

    public CouponResponse getGeneralCoupon(){
        try{
            return Optional.ofNullable(couponApiClient.getGeneralCoupon().getBody())
                    .map(BaseResponse::getData)
                    .orElseThrow(CouponApiClientException::new);
        }catch (FeignException e){
            throw new CouponApiClientException();
        }
    }

    public CouponResponse getBookCoupon(){
        try{
            return Optional.ofNullable(couponApiClient.getBookCoupon().getBody())
                    .map(BaseResponse::getData)
                    .orElseThrow(CouponApiClientException::new);
        }catch (FeignException e){
            throw new CouponApiClientException();
        }
    }

    public CouponResponse getCategoryCoupon(){
        try{
            return Optional.ofNullable(couponApiClient.getCategoryCoupon().getBody())
                    .map(BaseResponse::getData)
                    .orElseThrow(CouponApiClientException::new);
        }catch (FeignException e){
            throw new CouponApiClientException();
        }
    }
}
