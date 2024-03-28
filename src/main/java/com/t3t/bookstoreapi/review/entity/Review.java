package com.t3t.bookstoreapi.review.entity;

import com.sun.istack.NotNull;
import com.t3t.bookstoreapi.book.entity.Book;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long reviewId;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @NotNull
    @Column(name = "review_comment")
    private String reviewComment;

    @NotNull
    @Column(name = "review_score")
    private Integer reviewScore;

    @Column(name = "review_created_at")
    private LocalDateTime reviewCreatedAt;

    @Column(name = "review_updated_at")
    private LocalDateTime reviewUpdatedAt;

    @Builder
    public Review(Book book, String reviewComment, Integer reviewScore, LocalDateTime reviewCreatedAt, LocalDateTime reviewUpdatedAt) {
        this.book = book;
        this.reviewComment = reviewComment;
        this.reviewScore = reviewScore;
        this.reviewCreatedAt = reviewCreatedAt;
        this.reviewUpdatedAt = reviewUpdatedAt;
    }
}