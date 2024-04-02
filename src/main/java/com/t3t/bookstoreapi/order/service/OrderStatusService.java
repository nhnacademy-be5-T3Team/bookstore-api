package com.t3t.bookstoreapi.order.service;

import com.t3t.bookstoreapi.order.exception.OrderStatusNotFoundForIdException;
import com.t3t.bookstoreapi.order.exception.OrderStatusNotFoundForNameException;
import com.t3t.bookstoreapi.order.model.dto.OrderStatusDto;
import com.t3t.bookstoreapi.order.repository.OrderStatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OrderStatusService {
    private final OrderStatusRepository orderStatusRepository;


    /**
     * 모든 주문 상태를 조회한다.
     * @return 조회된 주문 상태들에 대한 DTO 리스트
     * @author woody35545(구건모)
     */
    @Transactional(readOnly = true)
    public List<OrderStatusDto> getAllOrderStatusList() {
        return orderStatusRepository.findAll().stream()
                .map(OrderStatusDto::of)
                .collect(Collectors.toList());
    }


    /**
     * 주문 상태 식별자로 주문 상태를 조회한다.
     * @param orderStatusId 조회하고자 하는 주문 상태 식별자
     * @return 조회된 주문 상태에 대한 DTO
     */
    @Transactional(readOnly = true)
    public OrderStatusDto getOrderStatusById(Long orderStatusId) {
        return OrderStatusDto.of(orderStatusRepository.findById(orderStatusId)
                .orElseThrow(() -> new OrderStatusNotFoundForIdException(orderStatusId)));
    }


    /**
     * 주문 상태 이름으로 주문 상태를 조회한다.
     * @param statusName 조회하고자 하는 주문 상태 이름
     * @return 조회된 주문 상태에 대한 DTO
     */
    @Transactional(readOnly = true)
    public OrderStatusDto getOrderStatusByName(String statusName) {
        return OrderStatusDto.of(orderStatusRepository.findByName(statusName)
                .orElseThrow(() -> new OrderStatusNotFoundForNameException(statusName)));
    }
}
