package com.t3t.bookstoreapi.publisher.repository;

import com.t3t.bookstoreapi.publisher.model.entity.Publisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {
    Optional<Publisher> findById(Long publisherId);
    Page<Publisher> findAll(Pageable pageable);
}
