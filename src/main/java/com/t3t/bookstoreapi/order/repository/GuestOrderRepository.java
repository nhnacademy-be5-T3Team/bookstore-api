package com.t3t.bookstoreapi.order.repository;

import com.t3t.bookstoreapi.order.model.entity.GuestOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestOrderRepository extends JpaRepository<GuestOrder, String> {
}
