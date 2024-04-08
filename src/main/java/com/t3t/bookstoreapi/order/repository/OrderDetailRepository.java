package com.t3t.bookstoreapi.order.repository;

import com.t3t.bookstoreapi.order.model.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long>, OrderDetailRepositoryCustom {

}
