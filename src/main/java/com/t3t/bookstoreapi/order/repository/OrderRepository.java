package com.t3t.bookstoreapi.order.repository;

import com.t3t.bookstoreapi.order.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {
}
