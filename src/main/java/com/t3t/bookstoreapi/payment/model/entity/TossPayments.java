package com.t3t.bookstoreapi.payment.model.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "toss_payments")
@Builder
public class TossPayments implements Serializable {

    @EmbeddedId
    private TossPaymentId tossPaymentId;

    @Column(name = "toss_payment_key")
    private String tossPaymentKey;

    @Column(name = "toss_order_id")
    private String tossOrderId;

    @Column(name = "toss_payment_status")
    private String tossPaymentStatus;

    @Column(name = "toss_payment_receipt_url")
    private String tossPaymentReceiptUrl;

    @Embeddable
    @Setter
    @Getter
    public static class TossPaymentId implements Serializable {
        @OneToOne
        @JoinColumn(name = "payment_id")
        private Payments payment;
    }
}


