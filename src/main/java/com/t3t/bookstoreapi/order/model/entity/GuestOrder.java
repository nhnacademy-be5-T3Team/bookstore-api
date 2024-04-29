package com.t3t.bookstoreapi.order.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.*;

/**
 * 비회원 주문 비밀번호 엔티티
 *
 * @author woody35545(구건모)
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "guest_orders")
public class GuestOrder {
    @Id
    @Column(name = "guest_order_id", length = 32)
    @Comment("비회원 주문 식별자")
    private String id;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    @Comment("주문 정보")
    private Order order;

    @Column(name = "guest_order_password", length = 15, nullable = false)
    @Comment("주문 비밀번호")
    private String password;
}
