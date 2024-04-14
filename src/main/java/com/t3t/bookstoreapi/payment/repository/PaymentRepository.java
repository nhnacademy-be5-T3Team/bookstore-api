package com.t3t.bookstoreapi.payment.repository;

import com.t3t.bookstoreapi.payment.model.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long>{
}
