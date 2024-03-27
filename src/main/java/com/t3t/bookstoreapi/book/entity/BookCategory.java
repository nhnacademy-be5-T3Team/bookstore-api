package com.t3t.bookstoreapi.book.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Entity
@Table(name = "book_categories")
public class BookCategory {
    @EmbeddedId
    private BookCategoryId id;

    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    @Embeddable
    public static class BookCategoryId implements Serializable {
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "book_id")
        private Book book;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "category_id")
        private Category category;
    }

}