package com.t3t.bookstoreapi.book.repository;

import com.t3t.bookstoreapi.book.model.entity.BookImage;
import com.t3t.bookstoreapi.book.model.entity.BookTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookImageRepository extends JpaRepository<BookImage, Long> {
    List<BookImage> findByBookBookId(Long bookId);
}
