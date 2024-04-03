package com.t3t.bookstoreapi.order.repository;

import com.t3t.bookstoreapi.order.model.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long> {
    Optional<OrderStatus> findByName(String name);
}
