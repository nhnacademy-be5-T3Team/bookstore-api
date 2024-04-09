package com.t3t.bookstoreapi.payment.repository;

import com.t3t.bookstoreapi.payment.model.entity.TossPayments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TossPaymentRepository extends JpaRepository<TossPayments, TossPayments.TossPaymentId> {
    Optional<TossPayments> findByTossPaymentIdPayment(Long paymentId);
    Optional<TossPayments> findByTossPaymentKey(String tossPaymentKey);

}
