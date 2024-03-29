package com.t3t.bookstoreapi.order.repository;

import com.t3t.bookstoreapi.order.model.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long>{
}
