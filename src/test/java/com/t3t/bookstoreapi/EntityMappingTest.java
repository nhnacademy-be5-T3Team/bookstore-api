package com.t3t.bookstoreapi;

import com.t3t.bookstoreapi.order.model.entity.OrderStatus;
import com.t3t.bookstoreapi.order.repository.OrderStatusRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Slf4j
@ActiveProfiles("prod")
class EntityMappingTest {

    @Autowired
    private OrderStatusRepository orderStatusRepository;

    @Test
    @DisplayName("OrderStatus 엔티티 테스트")
    void orderStatusTest() {

        // given
        String orderStatusName = "testOrderStatus";

        // when
        OrderStatus orderStatus = orderStatusRepository.save(OrderStatus.builder().name(orderStatusName).build());

        // then
        Assertions.assertNotNull(orderStatusRepository.findById(orderStatus.getId()));
        Assertions.assertEquals(orderStatusName, orderStatus.getName());
    }
}
