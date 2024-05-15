package com.t3t.bookstoreapi.book.repository;

import com.t3t.bookstoreapi.book.model.entity.BookThumbnail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookThumbnailRepository extends JpaRepository<BookThumbnail, Long> {
    /**
     * 특정 도서에 대한 썸네일을 조회
     * @param bookId 도서 식별자
     * @return 해당 도서의 썸네일
     * @author Yujin-nKim(김유진)
     */
    BookThumbnail findByBookBookId(Long bookId);
}
