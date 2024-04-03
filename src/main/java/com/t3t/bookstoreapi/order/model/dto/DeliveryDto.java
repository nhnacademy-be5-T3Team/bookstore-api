package com.t3t.bookstoreapi.order.model.dto;

import com.t3t.bookstoreapi.order.model.entity.Delivery;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryDto {
    // 배송 식별자
    private Long id;
    // 배송비
    private BigDecimal price;
    // 배송 우편 주소
    private int addressNumber;
    // 배송 도로명 주소
    private String roadnameAddress;
    // 배송 상세 주소
    private String detailAddress;
    // 배송 일자
    private LocalDate deliveryDate;
    // 배송 수령인 이름
    private String recipientName;
    // 배송 수령인 전화번호
    private String recipientPhoneNumber;

    public static DeliveryDto of(Delivery delivery) {
        return DeliveryDto.builder()
                .id(delivery.getId())
                .price(delivery.getPrice())
                .addressNumber(delivery.getAddressNumber())
                .roadnameAddress(delivery.getRoadnameAddress())
                .detailAddress(delivery.getDetailAddress())
                .deliveryDate(delivery.getDeliveryDate())
                .recipientName(delivery.getRecipientName())
                .recipientPhoneNumber(delivery.getRecipientPhoneNumber())
                .build();
    }
}
