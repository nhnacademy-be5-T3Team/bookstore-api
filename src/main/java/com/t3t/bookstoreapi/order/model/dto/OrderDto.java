package com.t3t.bookstoreapi.order.model.dto;

import com.t3t.bookstoreapi.order.model.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 주문 정보 DTO
 * @author woody35545(구건모)
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Long id; // 주문 식별자
    private Long memberId; // 주문인
    private Long deliveryId; // 배송 정보 식별자
    private LocalDateTime createdAt; // 주문 일시

    public static OrderDto of(Order order){
        return OrderDto.builder()
                .id(order.getId())
                .memberId(order.getMember().getId())
                .deliveryId(order.getDelivery().getId())
                .createdAt(order.getCreatedAt())
                .build();
    }
}
