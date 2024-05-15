package com.t3t.bookstoreapi.review.repository;

import com.t3t.bookstoreapi.review.model.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryCustom{
    /**
     * 특정 회원과 특정 도서에 대한 리뷰가 이미 등록되어 있는지 확인
     * @param memberId 회원 ID
     * @param bookId   도서 ID
     * @return 특정 회원이 특정 도서에 대한 리뷰가 이미 등록되어 있는지 여부
     * @author Yujin-nKim(김유진)
     */
    boolean existsByBookBookIdAndAndMemberId(Long bookId, Long memberId);

    /**
     * 도서 ID에 해당하는 리뷰의 수를 조회
     * @param bookId 도서 ID
     * @return 도서에 대한 리뷰의 수
     * @author Yujin-nKim(김유진)
     */
    Integer countByBookBookId(Long bookId);

    /**
     * 리뷰 평점을 계산
     * @param bookId   도서 ID
     * @return 계산한 리뷰 평점
     * @author Yujin-nKim(김유진)
     */
    @Query("SELECT AVG(r.reviewScore) FROM Review r WHERE r.book.bookId = :bookId")
    Float getAverageScoreByBookId(Long bookId);
}
