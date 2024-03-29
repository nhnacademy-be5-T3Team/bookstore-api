package com.t3t.bookstoreapi.entity;

import com.t3t.bookstoreapi.book.model.entity.*;
import com.t3t.bookstoreapi.book.repository.*;
import com.t3t.bookstoreapi.category.model.entity.Category;
import com.t3t.bookstoreapi.category.repository.CategoryRepository;
import com.t3t.bookstoreapi.publisher.model.entity.Publisher;
import com.t3t.bookstoreapi.publisher.repository.PublisherRepository;
import com.t3t.bookstoreapi.tag.model.entity.Tag;
import com.t3t.bookstoreapi.tag.repository.TagRepository;
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
    private TagRepository tagRepository;
    @Autowired
    private BookCategoryRepository bookCategoryRepository;
    @Autowired
    private BookImageRepository bookImageRepository;
    @Autowired
    private BookTagRepository bookTagRepository;
    @Autowired
    private BookThumbnailRepository bookThumbnailRepository;

    private Book testBook;

    @BeforeEach
    void setUpBookEntity() {
        Publisher publisher = publisherRepository.save(Publisher.builder()
                .publisherName("TestPublisher")
                .publisherEmail("TestPublisher@example.com")
                .build());

        testBook = bookRepository.save(Book.builder()
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

    }

    @Test
    @DisplayName("BookCategory entity 맵핑 테스트")
    void testBookCategoryEntityMapping() {

        Category category = categoryRepository.save(Category.builder()
                .categoryName("categoryName")
                .build());

        BookCategory bookCategory = BookCategory.builder().book(testBook).category(category).build();

        bookCategoryRepository.save(bookCategory);
    }

    @Test
    @DisplayName("BookImage entity 맵핑 테스트")
    void testBookImageEntityMapping() {

        String bookImageUrl = "https://image.aladin.co.kr/product/27137/83/coversum/k252731342_1.jpg";

        BookImage bookImage = bookImageRepository.save(BookImage.builder()
                .book(testBook)
                .bookImageUrl(bookImageUrl)
                .build());

        bookImageRepository.save(bookImage);

        BookImage savedBookImaged = bookImageRepository.findById(bookImage.getBookImageId()).orElse(null);

        assertNotNull(savedBookImaged);
        assertEquals(bookImageUrl, savedBookImaged.getBookImageUrl());
    }

    @Test
    @DisplayName("BookTag entity 맵핑 테스트")
    void testBookTagEntityMapping() {

        Tag tag = tagRepository.save(Tag.builder().tagName("TestTagName").build());

        BookTag bookTag = BookTag.builder().book(testBook).tag(tag).build();

        bookTagRepository.save(bookTag);

        BookTag savedBookTag = bookTagRepository.findById(new BookTag.BookTagId(testBook, tag)).orElse(null);

        assertNotNull(savedBookTag);
        assertEquals("TestTagName", savedBookTag.getId().getTag().getTagName());
    }

    @Test
    @DisplayName("BookThumbnail entity 맵핑 테스트")
    void testBookThumbnailEntityMapping() {

        String thumbnailImageUrl = "https://image.aladin.co.kr/product/27137/83/coversum/k252731342_1.jpg";

        BookThumbnail bookThumbnail = BookThumbnail.builder()
                .book(testBook)
                .thumbnailImageUrl(thumbnailImageUrl)
                .build();

        bookThumbnailRepository.save(bookThumbnail);

        BookThumbnail savedBookThumbnail = bookThumbnailRepository.findById(bookThumbnail.getBookThumbnailImageId()).orElse(null);

        assertNotNull(savedBookThumbnail);
        assertEquals(thumbnailImageUrl, savedBookThumbnail.getThumbnailImageUrl());
    }

}
