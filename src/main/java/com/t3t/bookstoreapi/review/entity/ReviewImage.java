package com.t3t.bookstoreapi.review.entity;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "review_images")
public class ReviewImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_image_id")
    private Long reviewImageId;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;

    @NotNull
    @Column(name = "review_image_url")
    private String reviewImageUrl;

    @Builder
    public ReviewImage(Review review, String reviewImageUrl) {
        this.review = review;
        this.reviewImageUrl = reviewImageUrl;
    }
}
