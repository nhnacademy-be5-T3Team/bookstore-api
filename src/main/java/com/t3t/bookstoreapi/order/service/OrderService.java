package com.t3t.bookstoreapi.order.service;

import com.t3t.bookstoreapi.member.exception.MemberNotFoundForIdException;
import com.t3t.bookstoreapi.member.model.entity.Member;
import com.t3t.bookstoreapi.member.repository.MemberRepository;
import com.t3t.bookstoreapi.order.constant.OrderStatusType;
import com.t3t.bookstoreapi.order.exception.DeliveryNotFoundForIdException;
import com.t3t.bookstoreapi.order.exception.OrderNotFoundForIdException;
import com.t3t.bookstoreapi.order.exception.OrderStatusNotFoundForNameException;
import com.t3t.bookstoreapi.order.model.dto.OrderDto;
import com.t3t.bookstoreapi.order.model.entity.Order;
import com.t3t.bookstoreapi.order.model.entity.OrderDetail;
import com.t3t.bookstoreapi.order.model.request.GuestOrderCreationRequest;
import com.t3t.bookstoreapi.order.model.request.MemberOrderCreationRequest;
import com.t3t.bookstoreapi.order.repository.DeliveryRepository;
import com.t3t.bookstoreapi.order.repository.OrderDetailRepository;
import com.t3t.bookstoreapi.order.repository.OrderRepository;
import com.t3t.bookstoreapi.order.repository.OrderStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final MemberRepository memberRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final DeliveryRepository deliveryRepository;

    /**
     * 회원 주문 생성
     * @param request 회원 주문 생성 요청 객체
     * @return 생성된 주문 DTO
     * @autor woody35545(구건모)
     */
    public OrderDto createOrder(MemberOrderCreationRequest request) {
        return OrderDto.of(orderRepository.save(Order.builder()
                .member(memberRepository.findById(request.getMemberId())
                        .orElseThrow(() -> new MemberNotFoundForIdException(request.getMemberId())))
                .delivery(deliveryRepository.findById(request.getDeliveryId())
                        .orElseThrow(() -> new DeliveryNotFoundForIdException(request.getDeliveryId())))
                .createdAt(LocalDateTime.now())
                .build()));
    }

}