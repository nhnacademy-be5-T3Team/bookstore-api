package com.t3t.bookstoreapi.order.repository;

import com.t3t.bookstoreapi.order.model.response.OrderInfoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderRepositoryCustom {

    /**
     * 회원의 모든 주문 정보를 페이징 처리하여 조회
     *
     * @author woody35545(구건모)
     * @see OrderInfoResponse
     */
    Page<OrderInfoResponse> getOrderInfoResponseByMemberIdWithPaging(Long memberId, Pageable pageable);

}
