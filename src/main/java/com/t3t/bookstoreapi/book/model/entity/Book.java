package com.t3t.bookstoreapi.book.model.entity;

import javax.validation.constraints.NotNull;
import com.t3t.bookstoreapi.publisher.model.entity.Publisher;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

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
    private Integer bookPackage;

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

    @OneToOne(mappedBy = "book")
    private BookThumbnail bookThumbnail;

    @OneToMany(mappedBy = "book")
    List<ParticipantRoleRegistration> authors = new ArrayList<>();

    public void incrementLikes() {
        this.bookLikeCount++;
    }

    public void decreaseLikes() {
        this.bookLikeCount--;
    }

}
