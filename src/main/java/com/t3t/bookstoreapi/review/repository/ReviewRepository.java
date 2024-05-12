package com.t3t.bookstoreapi.review.repository;

import com.t3t.bookstoreapi.review.model.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryCustom{
}
