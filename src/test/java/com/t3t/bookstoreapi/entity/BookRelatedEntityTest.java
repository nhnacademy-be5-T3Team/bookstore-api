package com.t3t.bookstoreapi.entity;

import com.t3t.bookstoreapi.book.model.entity.Book;
import com.t3t.bookstoreapi.book.model.entity.BookCategory;
import com.t3t.bookstoreapi.book.model.entity.BookImage;
import com.t3t.bookstoreapi.book.repository.BookCategoryRepository;
import com.t3t.bookstoreapi.book.repository.BookImageRepository;
import com.t3t.bookstoreapi.book.repository.BookRepository;
import com.t3t.bookstoreapi.category.model.entity.Category;
import com.t3t.bookstoreapi.category.repository.CategoryRepository;
import com.t3t.bookstoreapi.publisher.model.entity.Publisher;
import com.t3t.bookstoreapi.publisher.repository.PublisherRepository;
import lombok.Setter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
@ActiveProfiles("prod")
class BookRelatedEntityTest {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private PublisherRepository publisherRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private BookCategoryRepository bookCategoryRepository;
    @Autowired
    private BookImageRepository bookImageRepository;
    @Setter
    private Book book;

    @BeforeEach
    void createBookEntity() {
        Publisher publisher = publisherRepository.save(Publisher.builder()
                .publisherName("TestPublisher")
                .publisherEmail("TestPublisher@example.com")
                .build());

        Book book = bookRepository.save(Book.builder()
                .publisher(publisher)
                .bookName("어린왕자")
                .bookIndex("예시 목차")
                .bookDesc("어린왕자는 황소와의 대화, 별의 왕과의 대화, 장미꽃과의 대화 등을 통해 인생의 진리를 탐구하는 내용이 담겨있다.")
                .bookIsbn("9788966863307")
                .bookPrice(new BigDecimal("19.99"))
                .bookDiscount(new BigDecimal("0.1"))
                .bookPackage(1)
                .bookPublished(LocalDate.of(1943, Month.APRIL, 6))
                .bookStock(100)
                .bookAverageScore(4.5f)
                .bookLikeCount(500)
                .build());

        setBook(book);
    }

    @Test
    @DisplayName("BookCategory entity 맵핑 테스트")
    void testBookCategoryEntityMapping() {

        Category category = categoryRepository.save(Category.builder()
                .categoryName("categoryName")
                .build());

        BookCategory bookCategory = BookCategory.builder().book(book).category(category).build();

        bookCategoryRepository.save(bookCategory);
    }

    @Test
    @DisplayName("BookImage entity 맵핑 테스트")
    void testBookImageEntityMapping() {

        String bookImageUrl = "https://image.aladin.co.kr/product/27137/83/coversum/k252731342_1.jpg";

        BookImage bookImage = bookImageRepository.save(BookImage.builder()
                .book(book)
                .bookImageUrl(bookImageUrl)
                .build());

        bookImageRepository.save(bookImage);

        BookImage savedBookImaged = bookImageRepository.findById(bookImage.getBookImageId()).orElse(null);

        assertNotNull(savedBookImaged);
        assertEquals(bookImageUrl, savedBookImaged.getBookImageUrl());
    }

}
