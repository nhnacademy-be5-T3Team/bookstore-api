package com.t3t.bookstoreapi.payment.repository;

import com.t3t.bookstoreapi.payment.constant.PaymentProviderType;
import com.t3t.bookstoreapi.payment.model.entity.PaymentProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentProviderRepository extends JpaRepository<PaymentProvider, Long>{
    Optional<PaymentProvider> findByName(PaymentProviderType providerName);
}
