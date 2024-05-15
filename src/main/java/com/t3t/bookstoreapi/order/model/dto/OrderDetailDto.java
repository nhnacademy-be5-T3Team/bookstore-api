package com.t3t.bookstoreapi.order.model.dto;

import com.t3t.bookstoreapi.order.model.entity.OrderDetail;
import com.t3t.bookstoreapi.order.model.entity.Packaging;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * 주문 상세 DTO
 *
 * @author woody35545(구건모)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDto {
    private Long id; // 주문 상세 식별자
    private Integer quantity; // 주문 수량
    /**
     * 주문 상세 항목 단건에 대한 최종 가격
     * (= 책 가격 * 할인율 + 포장지 가격)
     */
    private BigDecimal price;
    private LocalDateTime createdAt; // 주문 상세 생성 일시
    /**
     * order
     */
    private Long orderId; // 주문 상세 항목이 속한 주문 정보 식별자
    /**
     * book
     */
    private Long bookId; // 주문한 책 식별자
    private String bookName; // 주문한 책 이름
    private String bookPublisherName; // 주문한 책 출판사 이름
//    private BigDecimal bookPrice; /* 추후 결제 시점에 책 가격을 저장하는 필드 생성 후 변경 예정 */
//    private BigDecimal bookDiscount; /* 추후 결제 시점에 책 할인율을 저장하는 필드 생성 후 변경 예정 */

    /**
     * packaging
     */
    private Long packagingId; // 주문 상세 항목에 사용된 포장지 식별자
    private String packagingName; // 주문 상세 항목에 사용된 포장지 이름
    private BigDecimal packagingPrice; // 주문 상세 항목에 사용된 포장지 가격
    /**
     * orderStatus
     */
    private String orderStatusName; // 주문 상태 이름

    public static OrderDetailDto of(OrderDetail orderDetail) {

        return OrderDetailDto.builder()
                .id(orderDetail.getId())
                .quantity(orderDetail.getQuantity())
                .createdAt(orderDetail.getCreatedAt())
                .orderId(orderDetail.getOrder().getId())
                .bookId(orderDetail.getBook().getBookId())
                .bookName(orderDetail.getBook().getBookName())
                .bookPublisherName(orderDetail.getBook().getPublisher().getPublisherName())
                .price(orderDetail.getBook().getBookPrice())
                .packagingName(Optional.ofNullable(orderDetail.getPackaging())
                        .map(Packaging::getName).orElse(null))
                .packagingPrice(Optional.ofNullable(orderDetail.getPackaging())
                        .map(Packaging::getPrice).orElse(BigDecimal.ZERO))
                .orderStatusName(orderDetail.getOrderStatus().getName())
                .build();
    }
}