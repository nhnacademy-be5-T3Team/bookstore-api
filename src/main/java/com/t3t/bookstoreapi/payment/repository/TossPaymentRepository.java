package com.t3t.bookstoreapi.payment.repository;



import com.t3t.bookstoreapi.payment.entity.TossPayments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TossPaymentRepository extends JpaRepository<TossPayments, Long> {
}