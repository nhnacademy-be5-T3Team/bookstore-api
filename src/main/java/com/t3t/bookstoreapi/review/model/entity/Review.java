package com.t3t.bookstoreapi.review.model.entity;

import javax.validation.constraints.NotNull;
import com.t3t.bookstoreapi.book.model.entity.Book;
import com.t3t.bookstoreapi.member.model.entity.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long reviewId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

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

    @OneToMany(mappedBy = "review", fetch = FetchType.LAZY)
    List<ReviewImage> reviewImageList = new ArrayList<>();

    /**
     * 리뷰의 comment를 업데이트
     * @param comment 수정할 comment
     * @author Yujin-nKim(김유진)
     */
    public void updateReviewComment(String comment) {
        this.reviewComment = comment;
    }

    /**
     * 리뷰의 score를 업데이트
     * @param score 수정할 score
     * @author Yujin-nKim(김유진)
     */
    public void updateReviewScore(Integer score) {
        this.reviewScore = score;
    }
}
