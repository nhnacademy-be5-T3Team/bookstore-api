package com.t3t.bookstoreapi.order.model.dto;

import com.t3t.bookstoreapi.order.model.entity.GuestOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 비회원 주문에 대한 DTO
 *
 * @author woody35545(구건모)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GuestOrderDto {
    private String id;
    private Long orderId;

    public static GuestOrderDto of(GuestOrder guestOrder) {
        return GuestOrderDto.builder()
                .id(guestOrder.getId())
                .orderId(guestOrder.getOrder().getId())
                .build();
    }
}
