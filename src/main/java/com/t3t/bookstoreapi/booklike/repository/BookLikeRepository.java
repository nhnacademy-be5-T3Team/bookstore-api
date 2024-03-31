package com.t3t.bookstoreapi.booklike.repository;

import com.t3t.bookstoreapi.booklike.model.entity.BookLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookLikeRepository extends JpaRepository<BookLike, BookLike.BookLikeId> {
}
