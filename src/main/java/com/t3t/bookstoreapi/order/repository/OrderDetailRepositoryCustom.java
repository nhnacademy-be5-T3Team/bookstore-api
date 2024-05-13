package com.t3t.bookstoreapi.order.repository;

import com.t3t.bookstoreapi.order.model.dto.OrderDetailDto;
import com.t3t.bookstoreapi.order.model.entity.OrderDetail;
import com.t3t.bookstoreapi.recommendation.model.response.BookInfoBriefResponse;

import java.util.List;
import java.util.Optional;

public interface OrderDetailRepositoryCustom {
    /**
     * 주문 상세 식별자로 주문 상세 DTO 조회
     *
     * @author woody35545(구건모)
     */
    Optional<OrderDetailDto> getOrderDetailDtoById(long orderDetailId);

    /**
     * 주문 식별자로 주문 상세 DTO 리스트 조회
     *
     * @author woody35545(구건모)
     */
    List<OrderDetailDto> getOrderDetailDtoListByOrderId(long orderId);

    /**
     * 주문 식별자로 주문 상세 엔티티 리스트 조회
     *
     * @author woody35545(구건모)
     */
    List<OrderDetail> getOrderDetailListByOrderId(long orderId);

    List<BookInfoBriefResponse> getSalesCountPerBook(int maxCount);
}
