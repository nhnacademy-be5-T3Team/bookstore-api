package com.t3t.bookstoreapi.order.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 주문 상세와 관련된 정보를 반환하는 DTO
 *
 * @author woody35545(구건모)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailInfoResponse {

    private Long id; // 주문 상세 식별자
    private Integer quantity; // 주문 수량
    /**
     * price<br>
     * 주문 상세 항목 단건에 대한 최종 가격
     * (= 책 가격 * 할인율 + 포장지 가격)
     */
    private BigDecimal price; // 상품 최종 결제 금액

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

    /**
     * delivery
     */
    private Long deliveryId; // 배송 식별자
    private BigDecimal deliveryPrice; // 배송비
    private int addressNumber; // 우편 주소
    private String roadnameAddress; // 도로명 주소
    private String detailAddress; // 상세 주소
    private LocalDate deliveryDate; // 배송 예정 일자(희망 배송 일자)
    private String recipientName; // 수령인 이름
    private String recipientPhoneNumber; // 수령인 연락처

}
