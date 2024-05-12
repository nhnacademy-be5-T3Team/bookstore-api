package com.t3t.bookstoreapi.order.constant;

/**
 * 주문 상태(OrderStatus) 를 나타내는 Enum 클래스<br>
 * @apiNote 기본적인 주문 상태로 `대기`, `배송중`, `완료`, `반품`, `주문취소`를 가진다.
 * @author woody35545(구건모)
 */
public enum OrderStatusType {
    PENDING, // 결제 대기
    CONFIRMED, // 결제 완료
    DELIVERING, // 배송중
    DELIVERED, // 완료
    REFUNDED, // 반품
    CANCELED; // 주문취소
}
