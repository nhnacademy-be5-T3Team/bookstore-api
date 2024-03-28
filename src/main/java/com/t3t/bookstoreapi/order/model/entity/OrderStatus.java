package com.t3t.bookstoreapi.order.model.entity;

import org.hibernate.annotations.Comment;

import javax.persistence.*;

@Entity
@Table(name = "order_status")
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_status_id")
    @Comment("주문 상태 식별자")
    private Long id;

    @Column(name = "order_status_name", length = 20, nullable = false)
    @Comment("주문 상태명")
    private String name;
}
