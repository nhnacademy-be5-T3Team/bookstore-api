package com.t3t.bookstoreapi.review.service;

import com.t3t.bookstoreapi.book.exception.BookNotFoundException;
import com.t3t.bookstoreapi.book.model.entity.Book;
import com.t3t.bookstoreapi.book.repository.BookRepository;
import com.t3t.bookstoreapi.member.exception.MemberNotFoundException;
import com.t3t.bookstoreapi.member.repository.MemberRepository;
import com.t3t.bookstoreapi.review.model.entity.Review;
import com.t3t.bookstoreapi.model.response.PageResponse;
import com.t3t.bookstoreapi.review.model.response.ReviewResponse;
import com.t3t.bookstoreapi.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    /**
     * 책 ID에 해당하는 리뷰 페이지 조회
     * @param bookId   리뷰를 검색할 책의 ID
     * @param pageable 페이지 정보
     * @return 주어진 책 ID에 대한 리뷰 페이지 응답
     * @throws BookNotFoundException 주어진 ID에 해당하는 책이 존재하지 않을 경우 발생
     * @author Yujin-nKim(김유진)
     */
    @Transactional(readOnly = true)
    public PageResponse<ReviewResponse> findReviewsByBookId(Long bookId, Pageable pageable) {

        if (!bookRepository.existsById(bookId)) {
            throw new BookNotFoundException();
        }

        Page<Review> reviewsPage = reviewRepository.findReviewsByBookId(bookId, pageable);

        List<ReviewResponse> responses = reviewsPage.getContent().stream()
                .map(ReviewResponse::of)
                .collect(Collectors.toList());

        return PageResponse.<ReviewResponse>builder()
                .content(responses)
                .pageNo(reviewsPage.getNumber())
                .pageSize(reviewsPage.getSize())
                .totalElements(reviewsPage.getTotalElements())
                .totalPages(reviewsPage.getTotalPages())
                .last(reviewsPage.isLast())
                .build();
    }

    /**
     * 특정 회원과 특정 도서에 대한 리뷰가 이미 등록되어 있는지 확인
     * @param memberId 회원 ID
     * @param bookId   도서 ID
     * @return 특정 회원이 특정 도서에 대한 리뷰가 이미 등록되어 있는지 여부
     * @author Yujin-nKim(김유진)
     */
    public boolean existsReview(Long bookId, Long memberId) {
        if (!bookRepository.existsById(bookId)) {
            throw new BookNotFoundException();
        }
        if (!memberRepository.existsById(memberId)) {
            throw new MemberNotFoundException();
        }
        return reviewRepository.existsByBookBookIdAndAndMemberId(bookId, memberId);
    }
}
