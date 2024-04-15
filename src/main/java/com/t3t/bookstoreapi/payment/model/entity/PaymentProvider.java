package com.t3t.bookstoreapi.payment.model.entity;

import com.t3t.bookstoreapi.payment.constant.PaymentProviderType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;

import javax.persistence.*;

/**
 * 결제 서비스 제공자 엔티티 클래스<br>
 * @author woody35545(구건모)
 */
@SuperBuilder
@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "payment_providers")
public class PaymentProvider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_provider_id")
    @Comment("결제 서비스 제공자 식별자")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_provider_name", length = 30, nullable = false)
    @Comment("결제 서비스 제공자 이름")
    private PaymentProviderType name;
}
