package com.t3t.bookstoreapi.order.repository;


import com.t3t.bookstoreapi.order.model.entity.Packaging;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackagingRepository extends JpaRepository<Packaging, Long> {
}
