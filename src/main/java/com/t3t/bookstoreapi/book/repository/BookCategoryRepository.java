package com.t3t.bookstoreapi.book.repository;

import com.t3t.bookstoreapi.book.model.entity.BookCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BookCategoryRepository extends JpaRepository<BookCategory, Long>, BookCategoryCustom {
    List<BookCategory> findByBookBookId(Long bookId);
}
