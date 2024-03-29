package com.t3t.bookstoreapi.book.model.entity;

import com.t3t.bookstoreapi.category.model.entity.Category;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "book_categories")
public class BookCategory {
    @EmbeddedId
    private BookCategoryId id;

    @Builder
    public BookCategory(Book book, Category category) {
        this.id = new BookCategoryId(book, category);
    }


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