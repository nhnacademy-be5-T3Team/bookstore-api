package com.t3t.bookstoreapi.publisher.repository;

import com.t3t.bookstoreapi.publisher.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {
}
