package com.t3t.bookstoreapi.book.repository;

import com.t3t.bookstoreapi.book.enums.TableStatus;
import com.t3t.bookstoreapi.book.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long>, BookRepositoryCustom {

    Optional<Book> findByBookId(Long bookId);

    Boolean existsByBookIsbn(String isbn);

    /**
     * 주어진 도서 ISBN과 삭제 여부에 따라 도서가 존재하는지 여부를 확인
     * @param bookIsbn   도서 ISBN
     * @param isDeleted  삭제 여부를 나타내는 TableStatus
     * @return 도서가 존재하는지 여부 (true: 존재함, false: 존재하지 않음)
     * @author Yujin-nKim(김유진)
     */
    Boolean existsByBookIsbnAndIsDeleted(String bookIsbn, TableStatus isDeleted);
}
