package com.t3t.bookstoreapi.order.repository;

import com.t3t.bookstoreapi.order.model.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    @Query("SELECT od.book, SUM(od.quantity) as totalQuantity FROM OrderDetail od " +
            "JOIN OrderStatus os ON od.orderStatus.name = os.name " +
            "WHERE os.name IN ('PENDING', 'DELIVERING', 'DELIVERED') " +
            "GROUP BY od.book.bookId " +
            "ORDER BY SUM(od.quantity) DESC")
    List<Object[]> getSalesCountPerBook();
}
