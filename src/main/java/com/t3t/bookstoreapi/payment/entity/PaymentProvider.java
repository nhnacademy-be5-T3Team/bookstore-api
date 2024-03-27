package com.t3t.bookstoreapi.payment.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payment_providers")
public class PaymentProvider {
    @Id
    @Column(name = "payment_provider_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer paymentProviderId;

    @Column(name = "payment_provider_name")
    private String paymentProviderName;
}
