package com.t3t.bookstoreapi.order.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 주문 변경 엔티티
 * @author woody35545(구건모)
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "order_changes")
public class OrderChange {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_change_id")
    @Comment("주문 변경 식별자")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    @Comment("주문 변경 요청이 발생한 주문 정보")
    private Order order;

    @Column(name = "order_change_reason", columnDefinition = "TEXT", nullable = false)
    @Comment("주문 변경 사유")
    private String reason;

    @Column(name = "order_change_request_date", nullable = false)
    @Comment("주문 변경 요청 일시")
    private LocalDateTime requestDate;
}
