package com.t3t.bookstoreapi.booklike.entity;

import com.t3t.bookstoreapi.book.entity.Book;
import com.t3t.bookstoreapi.member.domain.Member;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "book_likes")
public class BookLike {
    @EmbeddedId
    private BookLikeId id;

    @Builder
    public BookLike(Book book, Member member) {
        this.id = new BookLikeId(book, member);
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @Embeddable
    public static class BookLikeId implements Serializable {
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "book_id")
        private Book book;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "member_id")
        private Member member;
    }

}