package com.t3t.bookstoreapi.book.repository;

import com.t3t.bookstoreapi.book.model.entity.BookCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BookCategoryRepository extends JpaRepository<BookCategory, Long>, BookCategoryCustom {
    /**
     * 특정 도서에 속한 도서 카테고리 목록을 조회
     * @param bookId 도서 식별자
     * @return 해당 도서에 속한 도서 카테고리 목록
     * @author Yujin-nKim(김유진)
     */
    List<BookCategory> findByBookBookId(Long bookId);
}
