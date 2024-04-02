package com.t3t.bookstoreapi.order.servcie;

import com.t3t.bookstoreapi.order.exception.OrderStatusNotFoundForIdException;
import com.t3t.bookstoreapi.order.exception.OrderStatusNotFoundForNameException;
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
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class OrderStatusServiceUnitTest {
    @Mock
    private OrderStatusRepository orderStatusRepository;

    @InjectMocks
    private OrderStatusService orderStatusService;

    /**
     * 주문 상태 전체 조회 테스트
     *
     * @author woody35545(구건모)
     * @see OrderStatusService#getAllOrderStatusList()
     */
    @Test
    @DisplayName("주문 상태 조회 - 전체 조회")
    void getAllOrderStatusListTest() {
        // given
        List<OrderStatus> testOrderStatusList = List.of(
                OrderStatus.builder().id(0L).name("testOrderStatus0").build(),
                OrderStatus.builder().id(1L).name("testOrderStatus1").build(),
                OrderStatus.builder().id(2L).name("testOrderStatus2").build()
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

    /**
     * 주문 상태 식별자로 주문 상태 조회 테스트
     *
     * @author woody35545(구건모)
     * @see OrderStatusService#getOrderStatusById(Long)
     */
    @Test
    @DisplayName("주문 상태 조회 - 식별자로 조회")
    void getOrderStatusByIdTest() {
        // given
        OrderStatus testOrderStatus = OrderStatus.builder().id(0L).name("testOrderStatus").build();

        Mockito.when(orderStatusRepository.findById(testOrderStatus.getId())).thenReturn(Optional.of(testOrderStatus));

        // when
        OrderStatusDto resultOrderStatusDto = orderStatusService.getOrderStatusById(testOrderStatus.getId());

        // then
        Assertions.assertEquals(testOrderStatus.getId(), resultOrderStatusDto.getId());
        Assertions.assertEquals(testOrderStatus.getName(), resultOrderStatusDto.getName());
    }

    /**
     * 주문 상태 식별자로 주문 상태 조회 테스트<br>
     * @apiNote 요청한 식별자에 대해 주문 상태가 존재하지 않는 경우 `OrderStatusNotFoundForIdException`가 발생해야 한다.
     * @see OrderStatusService#getOrderStatusById(Long)
     * @see OrderStatusNotFoundForIdException
     * @author woody35545(구건모)
     */
    @Test
    @DisplayName("주문 상태 조회 - 식별자로 조회(존재하지 않는 주문 상태 요청)")
    void getOrderStatusByIdExceptionTest() {
        // given
        Long testOrderStatusId = 0L;

        Mockito.when(orderStatusRepository.findById(testOrderStatusId)).thenReturn(Optional.empty());

        // when & then
        Assertions.assertThrows(OrderStatusNotFoundForIdException.class, () -> orderStatusService.getOrderStatusById(testOrderStatusId));
    }

    /**
     * 주문 상태명으로 주문 상태 조회 테스트
     * @see OrderStatusService#getOrderStatusByName(String)
     * @author woody35545(구건모)
     */
    @Test
    @DisplayName("주문 상태 조회 - 상태명으로 조회")
    void getOrderStatusByNameTest() {
        // given
        OrderStatus testOrderStatus = OrderStatus.builder().id(0L).name("testOrderStatus").build();

        Mockito.when(orderStatusRepository.findByName(testOrderStatus.getName())).thenReturn(Optional.of(testOrderStatus));

        // when
        OrderStatusDto resultOrderStatusDto = orderStatusService.getOrderStatusByName(testOrderStatus.getName());

        // then
        Assertions.assertEquals(testOrderStatus.getId(), resultOrderStatusDto.getId());
        Assertions.assertEquals(testOrderStatus.getName(), resultOrderStatusDto.getName());
    }
}
