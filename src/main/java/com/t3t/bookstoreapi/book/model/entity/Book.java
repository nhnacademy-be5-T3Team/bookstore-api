package com.t3t.bookstoreapi.book.model.entity;

import javax.validation.constraints.NotNull;

import com.t3t.bookstoreapi.book.enums.TableStatus;
import com.t3t.bookstoreapi.book.converter.TableStatusConverter;
import com.t3t.bookstoreapi.publisher.model.entity.Publisher;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 도서(books) 엔티티
 *
 * @author Yujin-nKim(김유진)
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long bookId;

    @NotNull
    @Column(name = "book_name")
    private String bookName;

    @NotNull
    @Column(name = "book_index")
    private String bookIndex;

    @NotNull
    @Column(name = "book_desc")
    private String bookDesc;

    @NotNull
    @Column(name = "book_isbn_13")
    private String bookIsbn;

    @NotNull
    @Column(name = "book_price")
    private BigDecimal bookPrice;

    @NotNull
    @Column(name = "book_discount")
    private BigDecimal bookDiscount;

    @NotNull
    @Column(name = "book_package")
    @Convert(converter = TableStatusConverter.class)
    private TableStatus bookPackage;

    @NotNull
    @Column(name = "book_published")
    private LocalDate bookPublished;

    @NotNull
    @Column(name = "book_stock")
    private Integer bookStock;

    @NotNull
    @Column(name = "book_average_score")
    private Float bookAverageScore;

    @NotNull
    @Column(name = "book_like_count")
    private Integer bookLikeCount;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    // todo : 도서 좋아요 동시성 문제 관련해 수정해야함
    public void incrementLikes() {
        this.bookLikeCount++;
    }

    public void decreaseLikes() {
        this.bookLikeCount--;
    }

}
