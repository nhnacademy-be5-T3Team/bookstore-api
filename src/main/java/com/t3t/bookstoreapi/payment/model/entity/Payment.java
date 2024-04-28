package com.t3t.bookstoreapi.payment.model.entity;

import com.t3t.bookstoreapi.order.model.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 결제 엔티티 클래스<br>
 * 결제 서비스 제공자에 따라 서브타입이 존재한다.<br>
 * 각 서브타입은 결제 서비스 제공자에서 사용되는 추가 정보를 가진다.
 * @author woody35545(구건모)
 */
@SuperBuilder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    @Comment("결제 식별자")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @Comment("주문 식별자")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_provider_id", nullable = false)
    @Comment("결제 서비스 제공자")
    private PaymentProvider paymentProvider;

    @Column(name = "payment_total_amount", nullable = false)
    @Comment("결제 금액")
    private BigDecimal totalAmount;

    @Column(name = "payment_created_at", length = 30, nullable = false)
    @Comment("결제 생성 시간")
    private LocalDateTime createdAt;

}
