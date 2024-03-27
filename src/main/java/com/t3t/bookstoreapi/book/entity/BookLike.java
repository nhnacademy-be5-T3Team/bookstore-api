package com.t3t.bookstoreapi.book.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Entity
@Table(name = "book_likes")
public class BookLike {
    @EmbeddedId
    private BookLikeId id;

    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @Embeddable
    public static class BookLikeId implements Serializable {
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "book_id")
        private Book book;

//        @ManyToOne(fetch = FetchType.LAZY)
//        @JoinColumn(name = "member_id")
//        private Member member;
        @Column(name = "member_id")
        private Long memberId;
    }

}