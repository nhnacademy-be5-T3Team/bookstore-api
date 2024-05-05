package com.t3t.bookstoreapi.book.repository;

import com.t3t.bookstoreapi.book.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long>, BookRepositoryCustom {

    Optional<Book> findByBookId(Long bookId);

    Boolean existsByBookIsbn(String isbn);
}
