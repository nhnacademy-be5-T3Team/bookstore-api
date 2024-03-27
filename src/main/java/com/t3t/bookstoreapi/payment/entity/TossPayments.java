package com.t3t.bookstoreapi.payment.entity;
import com.t3t.bookstoreapi.payment.responce.TossPaymentResponse;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "toss_payments")
@Builder
public class TossPayments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long paymentId;

    @Column(name = "toss_payment_key")
    private String tossPaymentKey;

    @Column(name = "toss_order_id")
    private String tossOrderId;

    @Column(name = "toss_payment_status")
    private String tossPaymentStatus;

    @Column(name = "toss_payment_receipt_url")
    private String tossPaymentReceiptUrl;


    }


