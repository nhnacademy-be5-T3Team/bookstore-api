package com.t3t.bookstoreapi.payment.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payments")
public class Payments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long paymentId;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Orders orderId;

    @ManyToOne
    @JoinColumn(name = "payment_provider_id")
    private PaymentProvider paymentProviderId;

    @Column(name = "payment_time")
    private LocalDateTime paymentTime;

    @Column(name = "payment_price")
    private BigDecimal paymentPrice;

}
