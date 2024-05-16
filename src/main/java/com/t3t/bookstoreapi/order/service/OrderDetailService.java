package com.t3t.bookstoreapi.order.service;

import com.t3t.bookstoreapi.book.exception.BookNotFoundForIdException;
import com.t3t.bookstoreapi.book.repository.BookRepository;
import com.t3t.bookstoreapi.order.exception.OrderDetailNotFoundForIdException;
import com.t3t.bookstoreapi.order.exception.OrderNotFoundForIdException;
import com.t3t.bookstoreapi.order.exception.PackagingNotFoundForIdException;
import com.t3t.bookstoreapi.order.model.dto.OrderDetailDto;
import com.t3t.bookstoreapi.order.model.entity.OrderDetail;
import com.t3t.bookstoreapi.order.model.entity.Packaging;
import com.t3t.bookstoreapi.order.model.request.OrderDetailCreationRequest;
import com.t3t.bookstoreapi.order.repository.OrderDetailRepository;
import com.t3t.bookstoreapi.order.repository.OrderRepository;
import com.t3t.bookstoreapi.order.repository.PackagingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;
    private final PackagingRepository packagingRepository;

    /**
     * 주문 상세 DTO 조회
     *
     * @param orderDetailId 조회하려는 주문 상세 식별자
     * @return 주문 상세 DTO
     * @author woody35545(구건모)
     */
    @Transactional(readOnly = true)
    public OrderDetailDto getOrderDetailDtoById(long orderDetailId) {
        return orderDetailRepository.getOrderDetailDtoById(orderDetailId)
                .orElseThrow(() -> new OrderDetailNotFoundForIdException(orderDetailId));
    }

    /**
     * 주문에 대한 주문 상세 DTO 리스트 조회
     *
     * @param orderId 조회하려는 주문 식별자
     * @return 주문 상세 DTO 리스트
     * @author woody35545(구건모)
     */
    @Transactional(readOnly = true)
    public List<OrderDetailDto> getOrderDetailDtoListByOrderId(long orderId) {
        return orderDetailRepository.getOrderDetailDtoListByOrderId(orderId);
    }

    /**
     * 주문 상세 생성
     *
     * @param request 주문 상세 생성 요청 객체
     * @return 생성된 주문 상세 DTO
     * @author woody35545(구건모)
     */
    public OrderDetailDto createOrderDetail(OrderDetailCreationRequest request) {

        return OrderDetailDto.of(orderDetailRepository.save(
                OrderDetail.builder()
                        .book(bookRepository.findById(request.getBookId())
                                .orElseThrow(() -> new BookNotFoundForIdException(request.getBookId())))
                        .order(orderRepository.findById(request.getOrderId())
                                .orElseThrow(() -> new OrderNotFoundForIdException(request.getOrderId())))
                        .quantity(request.getQuantity())
                        .orderStatus(request.getOrderStatus())
                        .price(request.getPrice())
                        .packaging(Optional.ofNullable(request.getPackagingId())
                                .map(packagingId -> packagingRepository.findById(packagingId)
                                        .orElseThrow(() -> new PackagingNotFoundForIdException(request.getPackagingId())))
                                .orElse(null)
                        )
                        .createdAt(LocalDateTime.now())
                        .build()));
    }


}
