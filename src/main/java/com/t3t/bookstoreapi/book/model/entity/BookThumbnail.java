package com.t3t.bookstoreapi.book.model.entity;

import javax.validation.constraints.NotNull;

import com.t3t.bookstoreapi.book.converter.TableStatusConverter;
import com.t3t.bookstoreapi.book.enums.TableStatus;
import lombok.*;

import javax.persistence.*;

/**
 * 도서 썸네일(book_thumbnails) 엔티티
 *
 * @author Yujin-nKim(김유진)
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Entity
@Table(name = "book_thumbnails")
public class BookThumbnail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_thumbnail_image_id")
    private Long bookThumbnailImageId;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @NotNull
    @Column(name = "thumbnail_image_url")
    private String thumbnailImageUrl;

    @NotNull
    @Column(name = "is_deleted")
    @Convert(converter = TableStatusConverter.class)
    private TableStatus isDeleted;
}
