package com.t3t.bookstoreapi.book.repository;

import com.t3t.bookstoreapi.book.model.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByBookIdIn(List<Long> ids);
}
