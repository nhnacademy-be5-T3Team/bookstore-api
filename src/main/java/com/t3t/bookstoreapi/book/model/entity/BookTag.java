package com.t3t.bookstoreapi.book.model.entity;

import com.t3t.bookstoreapi.tag.model.entity.Tag;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "book_tags")
public class BookTag {
    @EmbeddedId
    private BookTagId id;

    @Builder
    public BookTag(Book book, Tag tag) {
        this.id = new BookTagId(book, tag);
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @Embeddable
    public static class BookTagId implements Serializable {
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "book_id")
        private Book book;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "tag_id")
        private Tag tag;
    }

}
