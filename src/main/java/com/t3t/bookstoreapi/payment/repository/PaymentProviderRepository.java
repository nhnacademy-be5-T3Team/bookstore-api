package com.t3t.bookstoreapi.payment.repository;

import com.t3t.bookstoreapi.payment.entity.PaymentProvider;
import com.t3t.bookstoreapi.payment.entity.Payments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentProviderRepository extends JpaRepository<PaymentProvider, Long> {
    PaymentProvider findByPaymentProviderName(String paymentProviderName);
}
