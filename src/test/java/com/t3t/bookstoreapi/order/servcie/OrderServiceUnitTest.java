package com.t3t.bookstoreapi.order.servcie;

import com.t3t.bookstoreapi.member.model.entity.Member;
import com.t3t.bookstoreapi.member.repository.MemberRepository;
import com.t3t.bookstoreapi.order.model.dto.OrderDto;
import com.t3t.bookstoreapi.order.model.entity.Delivery;
import com.t3t.bookstoreapi.order.model.entity.Order;
import com.t3t.bookstoreapi.order.model.request.MemberOrderCreationRequest;
import com.t3t.bookstoreapi.order.repository.DeliveryRepository;
import com.t3t.bookstoreapi.order.repository.OrderRepository;
import com.t3t.bookstoreapi.order.service.OrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceUnitTest {
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private DeliveryRepository deliveryRepository;
    @InjectMocks
    private OrderService orderService;

    /**
     * 회원 주문 생성 테스트
     *
     * @autor woody35545(구건모)
     * @see OrderService#createOrder(MemberOrderCreationRequest)
     */

    @Test
    @DisplayName("주문 생성 테스트")
    void createOrderTest() {
        // given
        Member testMember = Member.builder().id(0L).build();
        Delivery testDelivery = Delivery.builder().id(0L).build();

        when(memberRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(testMember));
        when(deliveryRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(testDelivery));

        when(orderRepository.save(Mockito.any()))
                .thenReturn(Order.builder()
                        .id(0L)
                        .member(testMember)
                        .delivery(testDelivery)
                        .createdAt(LocalDateTime.now())
                        .build());

        final MemberOrderCreationRequest memberOrderCreationRequest =
                MemberOrderCreationRequest.builder()
                        .memberId(0L)
                        .deliveryId(0L)
                        .build();

        // when
        OrderDto orderDto = orderService.createOrder(memberOrderCreationRequest);

        // then
        assertEquals(testMember.getId(), orderDto.getMemberId());
        assertEquals(testDelivery.getId(), orderDto.getDeliveryId());
        assertNotNull(orderDto.getId());
        assertNotNull(orderDto.getCreatedAt());

    }

}
