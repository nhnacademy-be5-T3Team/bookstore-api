package com.t3t.bookstoreapi.order.model.entity;

import com.t3t.bookstoreapi.member.domain.Member;
import lombok.*;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 주문 정보 엔티티
 * @Author woody35545(구건모)
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    @Comment("주문 식별자")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    @Comment("주문한 회원")
    private Member member;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id", nullable = false)
    @Comment("주문에 대한 배송 정보")
    private Delivery delivery;

    @Column(name = "order_datetime" , nullable = false)
    @Comment("주문 일시")
    private LocalDateTime orderDatetime;
}
