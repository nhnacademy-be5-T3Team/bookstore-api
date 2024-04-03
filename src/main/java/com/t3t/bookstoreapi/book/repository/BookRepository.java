package com.t3t.bookstoreapi.book.repository;

import com.t3t.bookstoreapi.book.model.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    Page<Book> findByBookIdIn(List<Long> ids, Pageable pageable);

    Book findByBookId(Long bookId);

    List<Book> findByBookPublishedBetween(LocalDate startDate, LocalDate endDate);
}
