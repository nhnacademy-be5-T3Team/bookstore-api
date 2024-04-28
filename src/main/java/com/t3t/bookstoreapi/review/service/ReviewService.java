package com.t3t.bookstoreapi.review.service;

import com.t3t.bookstoreapi.book.exception.BookNotFoundException;
import com.t3t.bookstoreapi.book.repository.BookRepository;
import com.t3t.bookstoreapi.review.model.entity.Review;
import com.t3t.bookstoreapi.review.model.entity.ReviewImage;
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
    private final BookRepository bookRepository;
    private final ReviewRepository reviewRepository;

    @Transactional(readOnly = true)
    public PageResponse<ReviewResponse> findReviewsByBookId(Long bookId, Pageable pageable) {

        if (!bookRepository.existsById(bookId)) {
            throw new BookNotFoundException();
        }

        Page<Review> reviewsPage = reviewRepository.findByBookBookId(bookId, pageable);

        List<ReviewResponse> responses = reviewsPage.getContent().stream()
                .map(this::buildReviewResponse)
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

    public ReviewResponse buildReviewResponse(Review review) {
        List<String> reviewImgUrlList = review.getReviewImageList().stream()
                .map(ReviewImage::getReviewImageUrl)
                .collect(Collectors.toList());

        return ReviewResponse.builder()
                .name(review.getMember().getName())
                .comment(review.getReviewComment())
                .reviewScore(review.getReviewScore())
                .createdAt(review.getReviewCreatedAt())
                .updatedAt(review.getReviewUpdatedAt())
                .reviewImgUrlList(reviewImgUrlList)
                .build();
    }
}
