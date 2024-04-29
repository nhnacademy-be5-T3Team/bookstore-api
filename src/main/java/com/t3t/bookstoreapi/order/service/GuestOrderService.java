package com.t3t.bookstoreapi.order.service;

import com.t3t.bookstoreapi.order.exception.OrderNotFoundForIdException;
import com.t3t.bookstoreapi.order.model.dto.GuestOrderDto;
import com.t3t.bookstoreapi.order.model.entity.GuestOrder;
import com.t3t.bookstoreapi.order.model.request.GuestOrderCreationRequest;
import com.t3t.bookstoreapi.order.repository.GuestOrderRepository;
import com.t3t.bookstoreapi.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class GuestOrderService {
    private final GuestOrderRepository guestOrderRepository;
    private final OrderRepository orderRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    /**
     * 비회원이 주문 시 필요한 추가정보를 담고 있는 GuestOrder 생성
     * @param request GuestOrder 생성 요청 객체
     * @return 생성된 GuestOrder DTO
     * @autor woody35545(구건모)
     */
    public GuestOrderDto createGuestOrder(GuestOrderCreationRequest request) {
        guestOrderRepository.save(GuestOrder.builder()
                .id(UUID.randomUUID().toString().replace("-", ""))
                .order(orderRepository.findById(request.getOrderId()).orElseThrow(
                        () -> new OrderNotFoundForIdException(request.getOrderId())))
                .password(bCryptPasswordEncoder.encode(request.getPassword()))
                .build());

        return GuestOrderDto.of(guestOrderRepository.save(GuestOrder.builder()
                .id(UUID.randomUUID().toString().replace("-", ""))
                .order(orderRepository.findById(request.getOrderId()).orElseThrow(
                        () -> new OrderNotFoundForIdException(request.getOrderId())))
                .password(bCryptPasswordEncoder.encode(request.getPassword()))
                .build()));
    }
}
