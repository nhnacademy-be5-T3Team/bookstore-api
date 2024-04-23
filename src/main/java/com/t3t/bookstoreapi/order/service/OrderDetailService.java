package com.t3t.bookstoreapi.order.service;

import com.t3t.bookstoreapi.order.exception.OrderDetailNotFoundForIdException;
import com.t3t.bookstoreapi.order.model.dto.OrderDetailDto;
import com.t3t.bookstoreapi.order.repository.OrderDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;

    /**
     * 주문 상세 정보 조회
     * @param orderDetailId 조회하려는 주문 상세 식별자
     * @author woody35545(구건모)
     */
    @Transactional(readOnly = true)
    public OrderDetailDto getOrderDetailDtoById(long orderDetailId) {
        return orderDetailRepository.getOrderDetailDtoById(orderDetailId)
                .orElseThrow(() -> new OrderDetailNotFoundForIdException(orderDetailId));
    }
}