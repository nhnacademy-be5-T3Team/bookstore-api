package com.t3t.bookstoreapi.book.repository;

import com.t3t.bookstoreapi.book.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
