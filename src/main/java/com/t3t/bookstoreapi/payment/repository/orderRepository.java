package com.t3t.bookstoreapi.payment.repository;

import com.t3t.bookstoreapi.order.model.entity.Order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface orderRepository  extends JpaRepository<Order, Long> {
}
