package com.t3t.bookstoreapi.book.repository;

import com.t3t.bookstoreapi.book.model.entity.BookImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookImageRepository extends JpaRepository<BookImage, Long> {
    List<BookImage> findByBookBookId(Long bookId);

    /**
     * 특정 도서에 속한 이미지 목록을 조회
     * @param bookId 도서 식별자
     * @return 해당 도서에 속한 이미지 목록
     * @author Yujin-nKim(김유진)
     */
    List<BookImage> findAllByBookBookId(Long bookId);
}
