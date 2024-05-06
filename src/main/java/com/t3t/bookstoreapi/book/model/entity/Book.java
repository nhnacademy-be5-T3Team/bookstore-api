package com.t3t.bookstoreapi.book.model.entity;

import javax.validation.constraints.NotNull;

import com.t3t.bookstoreapi.book.enums.TableStatus;
import com.t3t.bookstoreapi.book.converter.TableStatusConverter;
import com.t3t.bookstoreapi.book.model.request.ModifyBookDetailRequest;
import com.t3t.bookstoreapi.order.exception.BookStockShortageException;
import com.t3t.bookstoreapi.publisher.model.entity.Publisher;
import lombok.*;
import org.springframework.data.jpa.repository.Lock;

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

    @NotNull
    @Column(name = "is_deleted")
    @Convert(converter = TableStatusConverter.class)
    private TableStatus isDeleted;

    // todo : 도서 좋아요 동시성 문제 관련해 수정해야함
    public void incrementLikes() {
        this.bookLikeCount++;
    }

    public void decreaseLikes() {
        this.bookLikeCount--;
    }

    /**
     * 재고 증가<br>
     * 동시성 문제가 발생하지 않도록 Lock 이 적용된 상태에서 사용한다.
     *
     * @param quantity 증가할 수량
     * @author woody35545(구건모)
     * @see com.t3t.bookstoreapi.book.repository.BookRepositoryCustom#getBookByIdUsingLock(Long)
     */
    public void increaseStock(int quantity) {
        this.bookStock += quantity;
    }

    /**
     * 재고 감소<br>
     * 동시성 문제가 발생하지 않도록 Lock 이 적용된 상태에서 사용한다.
     *
     * @param quantity 감소할 수량
     * @author woody35545(구건모)
     * @see com.t3t.bookstoreapi.book.repository.BookRepositoryCustom#getBookByIdUsingLock(Long)
     */
    public void decreaseStock(int quantity) {
        if (this.bookStock < quantity) {
            throw new BookStockShortageException("재고가 부족합니다.");
        }

        this.bookStock -= quantity;
    }

    /**
     * 요청에 따라 도서 정보를 수정
     * @param request 수정할 도서의 상세 정보를 담은 요청 객체
     * @author Yujin-nKim(김유진)
     */
    public void updateBookDetails(ModifyBookDetailRequest request) {
        this.bookName = request.getBookTitle();
        this.bookIsbn = request.getBookIsbn();
        this.bookPrice = request.getBookPrice();
        this.bookDiscount = request.getBookDiscountRate();
        this.bookPackage = TableStatus.ofCode(request.getPackagingAvailableStatus());
        this.bookPublished = request.getBookPublished();
        this.bookStock = request.getBookStock();
        this.bookIndex = request.getBookIndex();
        this.bookDesc = request.getBookDesc();
    }

    /**
     * 도서의 출판사를 수정
     * @param publisher 수정할 출판사 객체
     * @author Yujin-nKim(김유진)
     */
    public void updatePublisher(Publisher publisher) {
        this.publisher = publisher;
    }
}
