package com.t3t.bookstoreapi.book.entity;

import com.sun.istack.NotNull;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

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
}
