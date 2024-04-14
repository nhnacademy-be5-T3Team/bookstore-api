package com.t3t.bookstoreapi.payment.repository;

import com.t3t.bookstoreapi.payment.model.entity.PaymentProvider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentProviderRepository extends JpaRepository<PaymentProvider, Long>{
}
