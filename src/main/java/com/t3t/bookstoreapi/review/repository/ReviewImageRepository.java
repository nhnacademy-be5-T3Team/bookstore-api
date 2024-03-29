package com.t3t.bookstoreapi.review.repository;

import com.t3t.bookstoreapi.review.model.entity.ReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewImageRepository extends JpaRepository<ReviewImage, Long> {
}
