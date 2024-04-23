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
                .quantity(1L)
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
}
