package com.t3t.bookstoreapi.order.servcie;

import com.t3t.bookstoreapi.order.model.dto.OrderStatusDto;
import com.t3t.bookstoreapi.order.model.entity.OrderStatus;
import com.t3t.bookstoreapi.order.repository.OrderStatusRepository;
import com.t3t.bookstoreapi.order.service.OrderStatusService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)

public class OrderStatusServiceUnitTest {
    @Mock
    private OrderStatusRepository orderStatusRepository;

    @InjectMocks
    private OrderStatusService orderStatusService;

    @Test
    @DisplayName("주문 상태 전체 조회 테스트")
    void getAllOrderStatusTest(){
        // given
        List<OrderStatus> testOrderStatusList = List.of(
            OrderStatus.builder().id(0L).name("testOrderStatus1").build(),
            OrderStatus.builder().id(1L).name("testOrderStatus2").build(),
            OrderStatus.builder().id(2L).name("testOrderStatus3").build()
        );

        Mockito.doReturn(testOrderStatusList).when(orderStatusRepository).findAll();

        // when
        List<OrderStatusDto> resultOrderStatusDtoList = orderStatusService.getAllOrderStatusList();

        // then
        Assertions.assertEquals(testOrderStatusList.size(), resultOrderStatusDtoList.size());

        for (int i = 0; i < testOrderStatusList.size(); i++) {
            Assertions.assertEquals(testOrderStatusList.get(i).getId(), resultOrderStatusDtoList.get(i).getId());
            Assertions.assertEquals(testOrderStatusList.get(i).getName(), resultOrderStatusDtoList.get(i).getName());
        }
    }
}
