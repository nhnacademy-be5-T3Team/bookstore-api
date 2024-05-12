package com.t3t.bookstoreapi.review.repository;

import com.t3t.bookstoreapi.review.model.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewRepositoryCustom {
    /**
     * 도서 ID에 해당하는 리뷰를 페이지별로 조회
     * @param bookId   도서 ID
     * @param pageable 페이징 정보
     * @return 해당 도서 ID에 대한 리뷰 페이지
     * @author Yujin-nKim(김유진)
     */
    Page<Review> findReviewsByBookId(Long bookId, Pageable pageable);
}
