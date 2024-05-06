package com.t3t.bookstoreapi.book.model.entity;

import com.t3t.bookstoreapi.book.converter.TableStatusConverter;
import com.t3t.bookstoreapi.book.enums.TableStatus;
import com.t3t.bookstoreapi.tag.model.entity.Tag;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * 도서 태그(book_tags) 엔티티
 *
 * @author Yujin-nKim(김유진)
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Entity
@Table(name = "book_tags")
public class BookTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="book_tag_id")
    private Long bookTagId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @NotNull
    @Column(name = "is_deleted")
    @Convert(converter = TableStatusConverter.class)
    private TableStatus isDeleted;
}
