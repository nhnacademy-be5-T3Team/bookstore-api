package com.t3t.bookstoreapi.order.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 비회원 주문 비밀번호 엔티티
 * @author woody35545(구건모)
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "guest_order_passwords")
public class GuestOrderPassword implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "guest_order_password_id")
    @Comment("비회원 주문 비밀번호 식별자")
    private Long id;

    @JoinColumn(name = "order_id", nullable = false)
    @Comment("주문 식별자")
    private Long orderId;

    @Column(name = "guest_order_password", length = 15, nullable = false)
    @Comment("주문 비밀번호")
    private String password;
}
