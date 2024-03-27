package com.t3t.bookstoreapi.book.entity;

import com.sun.istack.NotNull;
import lombok.Getter;

import javax.persistence.*;

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
}
