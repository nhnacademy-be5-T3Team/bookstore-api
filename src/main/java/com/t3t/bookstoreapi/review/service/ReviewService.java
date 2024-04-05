package com.t3t.bookstoreapi.review.service;

import com.t3t.bookstoreapi.review.model.entity.Review;
import com.t3t.bookstoreapi.review.model.entity.ReviewImage;
import com.t3t.bookstoreapi.review.model.response.ReviewResponse;
import com.t3t.bookstoreapi.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    @Transactional(readOnly = true)
    public Page<ReviewResponse> findReviewsByBookId(Long bookId, Pageable pageable) {

        Page<Review> reviewsPage = reviewRepository.findByBookBookId(bookId, pageable);

        List<ReviewResponse> responses = reviewsPage.getContent().stream()
                .map(this::buildReviewResponse)
                .collect(Collectors.toList());

        return new PageImpl<>(responses, pageable, reviewsPage.getTotalElements());
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
