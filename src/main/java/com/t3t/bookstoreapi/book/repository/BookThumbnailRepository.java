package com.t3t.bookstoreapi.book.repository;

import com.t3t.bookstoreapi.book.model.entity.BookThumbnail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookThumbnailRepository extends JpaRepository<BookThumbnail, Long> {
}
