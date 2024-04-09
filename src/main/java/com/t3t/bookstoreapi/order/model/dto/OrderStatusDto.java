package com.t3t.bookstoreapi.order.model.dto;

import com.t3t.bookstoreapi.order.model.entity.OrderStatus;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusDto {
    private Long id;
    private String name;

    public static OrderStatusDto of(OrderStatus orderStatus) {
        return OrderStatusDto.builder()
                .id(orderStatus.getId())
                .name(orderStatus.getName())
                .build();
    }
}
