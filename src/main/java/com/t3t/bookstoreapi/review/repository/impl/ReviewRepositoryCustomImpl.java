package com.t3t.bookstoreapi.review.repository.impl;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.t3t.bookstoreapi.review.model.entity.Review;
import com.t3t.bookstoreapi.review.repository.ReviewRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.t3t.bookstoreapi.book.model.entity.QBook.book;
import static com.t3t.bookstoreapi.member.model.entity.QMember.member;
import static com.t3t.bookstoreapi.review.model.entity.QReview.review;
import static com.t3t.bookstoreapi.review.model.entity.QReviewImage.reviewImage;

@RequiredArgsConstructor
public class ReviewRepositoryCustomImpl implements ReviewRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    /**
     * 도서 ID에 해당하는 리뷰를 페이지별로 조회
     * @param bookId   도서 ID
     * @param pageable 페이징 정보
     * @return 해당 도서 ID에 대한 리뷰 페이지
     * @author Yujin-nKim(김유진)
     */
    @Override
    public Page<Review> findReviewsByBookId(Long bookId, Pageable pageable) {

        List<Review> reviewList = jpaQueryFactory.selectFrom(review)
                .leftJoin(review.book, book).fetchJoin()
                .leftJoin(review.member, member).fetchJoin()
                .leftJoin(review.reviewImageList, reviewImage).fetchJoin()
                .where(review.book.bookId.eq(bookId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(review.count())
                .from(review)
                .where(review.book.bookId.eq(bookId));

        return PageableExecutionUtils.getPage(reviewList, pageable, countQuery::fetchOne);
    }
}