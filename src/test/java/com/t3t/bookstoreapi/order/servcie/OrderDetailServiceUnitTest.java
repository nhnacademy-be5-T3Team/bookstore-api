package com.t3t.bookstoreapi.order.servcie;

import com.t3t.bookstoreapi.order.model.dto.OrderDetailDto;
import com.t3t.bookstoreapi.order.repository.OrderDetailRepository;
import com.t3t.bookstoreapi.order.service.OrderDetailService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderDetailServiceUnitTest {
    @Mock
    private OrderDetailRepository orderDetailRepository;
    @InjectMocks
    private OrderDetailService orderDetailService;

    /**
     * 주문 상세 조회 - 주문 상세 식별자로 조회
     *
     * @author woody35545(구건모)
     * @see OrderDetailService#getOrderDetailDtoById(long)
     */
    @Test
    @DisplayName("주문 상세 조회 - 주문 상세 식별자로 조회")
    void getOrderDetailDtoByIdTest() {
        // given
        long orderDetailId = 0L;
        OrderDetailDto testOrderDetailDto = OrderDetailDto.builder()
                .id(0L)
                .quantity(1)
                .createdAt(LocalDateTime.now())
                .orderId(0L)
                .bookId(0L)
                .bookName("testBookName")
                .bookPublisherName("testPublisherName")
                .bookDiscount(new BigDecimal("0"))
                .bookPrice(new BigDecimal("10000"))
                .packagingName("testPackagingName")
                .packagingPrice(new BigDecimal("1000"))
                .orderStatusName("testOrderStatusName")
                .build();

        when(orderDetailRepository.getOrderDetailDtoById(orderDetailId)).thenReturn(
                Optional.of(testOrderDetailDto));

        // when
        OrderDetailDto resultOrderDetailDto = orderDetailService.getOrderDetailDtoById(orderDetailId);

        // then
        assertEquals(testOrderDetailDto, resultOrderDetailDto);
        assertEquals(testOrderDetailDto.getId(), resultOrderDetailDto.getId());
        assertEquals(testOrderDetailDto.getQuantity(), resultOrderDetailDto.getQuantity());
        assertEquals(testOrderDetailDto.getCreatedAt(), resultOrderDetailDto.getCreatedAt());
        assertEquals(testOrderDetailDto.getOrderId(), resultOrderDetailDto.getOrderId());
        assertEquals(testOrderDetailDto.getBookId(), resultOrderDetailDto.getBookId());
        assertEquals(testOrderDetailDto.getBookName(), resultOrderDetailDto.getBookName());
        assertEquals(testOrderDetailDto.getBookPublisherName(), resultOrderDetailDto.getBookPublisherName());
        assertEquals(testOrderDetailDto.getBookPrice(), resultOrderDetailDto.getBookPrice());
        assertEquals(testOrderDetailDto.getBookDiscount(), resultOrderDetailDto.getBookDiscount());
        assertEquals(testOrderDetailDto.getPackagingName(), resultOrderDetailDto.getPackagingName());
        assertEquals(testOrderDetailDto.getPackagingPrice(), resultOrderDetailDto.getPackagingPrice());
        assertEquals(testOrderDetailDto.getOrderStatusName(), resultOrderDetailDto.getOrderStatusName());
    }


    /**
     * 주문 상세 조회 - 주문 식별자로 주문 상세 리스트 조회
     * @see OrderDetailService#getOrderDetailDtoListByOrderId(long)
     * @author woody35545(구건모)
     */
    @Test
    @DisplayName("주문 상세 조회 - 주문 식별자로 주문 상세 리스트 조회")
    void getOrderDetailDtoListByOrderIdTest() {
        // given
        long orderId = 0L;
        OrderDetailDto testOrderDetailDto = OrderDetailDto.builder()
                .id(0L)
                .quantity(1)
                .createdAt(LocalDateTime.now())
                .orderId(0L)
                .bookId(0L)
                .bookName("testBookName")
                .bookPublisherName("testPublisherName")
                .bookDiscount(new BigDecimal("0"))
                .bookPrice(new BigDecimal("10000"))
                .packagingName("testPackagingName")
                .packagingPrice(new BigDecimal("1000"))
                .orderStatusName("testOrderStatusName")
                .build();

        when(orderDetailRepository.getOrderDetailDtoListByOrderId(orderId)).thenReturn(
                List.of(testOrderDetailDto));

        // when
        List<OrderDetailDto> resultOrderDetailDtoList = orderDetailService.getOrderDetailDtoListByOrderId(orderId);

        // then
        assertEquals(1, resultOrderDetailDtoList.size());
        assertEquals(testOrderDetailDto, resultOrderDetailDtoList.get(0));
        assertEquals(testOrderDetailDto.getId(), resultOrderDetailDtoList.get(0).getId());
        assertEquals(testOrderDetailDto.getQuantity(), resultOrderDetailDtoList.get(0).getQuantity());
        assertEquals(testOrderDetailDto.getCreatedAt(), resultOrderDetailDtoList.get(0).getCreatedAt());
        assertEquals(testOrderDetailDto.getOrderId(), resultOrderDetailDtoList.get(0).getOrderId());
        assertEquals(testOrderDetailDto.getBookId(), resultOrderDetailDtoList.get(0).getBookId());
        assertEquals(testOrderDetailDto.getBookName(), resultOrderDetailDtoList.get(0).getBookName());
        assertEquals(testOrderDetailDto.getBookPublisherName(), resultOrderDetailDtoList.get(0).getBookPublisherName());
        assertEquals(testOrderDetailDto.getBookPrice(), resultOrderDetailDtoList.get(0).getBookPrice());
        assertEquals(testOrderDetailDto.getBookDiscount(), resultOrderDetailDtoList.get(0).getBookDiscount());
        assertEquals(testOrderDetailDto.getPackagingName(), resultOrderDetailDtoList.get(0).getPackagingName());
        assertEquals(testOrderDetailDto.getPackagingPrice(), resultOrderDetailDtoList.get(0).getPackagingPrice());
        assertEquals(testOrderDetailDto.getOrderStatusName(), resultOrderDetailDtoList.get(0).getOrderStatusName());
    }
}
