package com.t3t.bookstoreapi;

import com.t3t.bookstoreapi.order.model.entity.Delivery;
import com.t3t.bookstoreapi.order.model.entity.OrderStatus;
import com.t3t.bookstoreapi.order.repository.DeliveryRepository;
import com.t3t.bookstoreapi.order.repository.OrderStatusRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
@Transactional
@Slf4j
@ActiveProfiles("prod")
class EntityMappingTest {

    @Autowired
    private OrderStatusRepository orderStatusRepository;

    @Autowired
    private DeliveryRepository deliveryRepository;

    /**
     * OrderStatus 엔티티 맵핑 테스트
     * @author woody33545(구건모)
     */
    @Test
    @DisplayName("OrderStatus 엔티티 맵핑 테스트")
    void orderStatusTest() {
        // given
        String orderStatusName = "testOrderStatus";
        OrderStatus orderStatus = orderStatusRepository.save(OrderStatus.builder().name(orderStatusName).build());

        // when
        Optional<OrderStatus> resultOrderStatus = orderStatusRepository.findById(orderStatus.getId());

        // then
        Assertions.assertTrue(resultOrderStatus.isPresent());
        Assertions.assertEquals(orderStatusName, resultOrderStatus.get().getName());
    }

    /**
     * Delivery 엔티티 맵핑 테스트
     * @author woody33545(구건모)
     */
    @Test
    @DisplayName("Delivery 엔티티 맵핑 테스트")
    void deliveryTest() {
        // given
        BigDecimal price = BigDecimal.valueOf(10000);
        int addressNumber = 12345;
        String roadnameAddress = "대전광역시 유성구";
        String detailAddress = "궁동";
        String recipientName = "woody";
        String recipientPhoneNumber = "010-1234-5678";
        LocalDate deliveryDate = LocalDate.now();

        Delivery delivery = deliveryRepository.save(Delivery.builder()
                .price(price)
                .addressNumber(addressNumber)
                .roadnameAddress(roadnameAddress)
                .detailAddress(detailAddress)
                .recipientName(recipientName)
                .recipientPhoneNumber(recipientPhoneNumber)
                .deliveryDate(deliveryDate)
                .build());

        // when
        Optional<Delivery> resultDelivery = deliveryRepository.findById(delivery.getId());

        // then
        Assertions.assertTrue(resultDelivery.isPresent());
        Assertions.assertEquals(price, resultDelivery.get().getPrice());
        Assertions.assertEquals(addressNumber, resultDelivery.get().getAddressNumber());
        Assertions.assertEquals(roadnameAddress, resultDelivery.get().getRoadnameAddress());
        Assertions.assertEquals(detailAddress, resultDelivery.get().getDetailAddress());
        Assertions.assertEquals(recipientName, resultDelivery.get().getRecipientName());
        Assertions.assertEquals(recipientPhoneNumber, resultDelivery.get().getRecipientPhoneNumber());
        Assertions.assertEquals(deliveryDate, resultDelivery.get().getDeliveryDate());
    }
}
