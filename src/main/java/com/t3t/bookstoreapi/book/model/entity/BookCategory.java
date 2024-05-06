package com.t3t.bookstoreapi.book.model.entity;

import com.t3t.bookstoreapi.book.converter.TableStatusConverter;
import com.t3t.bookstoreapi.book.enums.TableStatus;
import com.t3t.bookstoreapi.category.model.entity.Category;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * 도서 카테고리(book_categories) 엔티티
 *
 * @author Yujin-nKim(김유진)
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Entity
@Table(name = "book_categories")
public class BookCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="book_category_id")
    private Long bookCategoryId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @NotNull
    @Column(name = "is_deleted")
    @Convert(converter = TableStatusConverter.class)
    private TableStatus isDeleted;
}