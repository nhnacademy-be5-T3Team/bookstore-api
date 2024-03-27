package com.t3t.bookstoreapi.book.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name="books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="book_id", nullable = false)
    private Long bookId;

    @ManyToOne
    @JoinColumn(name = "publisher_id", nullable = false)
    private Publisher publisher;

    @Column(name="book_name", nullable = false)
    private String bookName;

    @Column(name="book_index", nullable = false)
    private String bookIndex;

    @Column(name="book_desc", nullable = false)
    private String bookDesc;

    @Column(name="book_isbn_13", nullable = false)
    private String bookIsbn;

    @Column(name="book_price", nullable = false)
    private BigDecimal bookPrice;

    @Column(name="book_discount", nullable = false)
    private BigDecimal bookDiscount;

    @Column(name="book_package", nullable = false)
    private Integer bookPackage;

    @Column(name="book_published", nullable = false)
    private LocalDate bookPublished;

    @Column(name="book_stock", nullable = false)
    private Integer bookStock;

    @Column(name="book_average_score", nullable = false)
    private Integer bookAverageScore;

    @Column(name="book_like_count", nullable = false)
    private Integer bookLikeCount;

}
