package com.t3t.bookstoreapi.payment.entity;

import com.t3t.bookstoreapi.order.model.entity.Order;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payments")
public class Payments implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long paymentId;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order orderId;

    @ManyToOne
    @JoinColumn(name = "payment_provider_id")
    private PaymentProvider paymentProviderId;

    @Column(name = "payment_time")
    private LocalDateTime paymentTime;

    @Column(name = "payment_price")
    private BigDecimal paymentPrice;

}
