package com.t3t.bookstoreapi.payment.repository;

import com.t3t.bookstoreapi.payment.model.entity.Payments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payments, Long> {
}
