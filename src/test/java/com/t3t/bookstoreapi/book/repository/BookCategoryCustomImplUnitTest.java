package com.t3t.bookstoreapi.book.repository;

import com.t3t.bookstoreapi.book.enums.TableStatus;
import com.t3t.bookstoreapi.book.model.entity.Book;
import com.t3t.bookstoreapi.book.model.entity.BookCategory;
import com.t3t.bookstoreapi.book.model.entity.BookThumbnail;
import com.t3t.bookstoreapi.book.model.response.BookDetailResponse;
import com.t3t.bookstoreapi.category.model.entity.Category;
import com.t3t.bookstoreapi.category.repository.CategoryRepository;
import com.t3t.bookstoreapi.config.DataSourceConfig;
import com.t3t.bookstoreapi.config.DatabasePropertiesConfig;
import com.t3t.bookstoreapi.config.QueryDslConfig;
import com.t3t.bookstoreapi.config.RestTemplateConfig;
import com.t3t.bookstoreapi.keymanager.service.SecretKeyManagerService;
import com.t3t.bookstoreapi.property.SecretKeyManagerProperties;
import com.t3t.bookstoreapi.property.SecretKeyProperties;
import com.t3t.bookstoreapi.publisher.model.entity.Publisher;
import com.t3t.bookstoreapi.publisher.repository.PublisherRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * BookCategoryCustomImpl 단위 테스트 <br>
 * 1. 카테고리 리스트에 해당하는 도서를 반환하는지 테스트 <br>
 *
 * @author Yujin-nKim(김유진)
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({DataSourceConfig.class, DatabasePropertiesConfig.class,
        QueryDslConfig.class, RestTemplateConfig.class,
        SecretKeyManagerService.class, SecretKeyManagerProperties.class, SecretKeyProperties.class})
@ActiveProfiles("test")
class BookCategoryCustomImplUnitTest {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private PublisherRepository publisherRepository;
    @Autowired
    private BookCategoryRepository bookCategoryRepository;
    @Autowired
    private BookThumbnailRepository bookThumbnailRepository;

    @DisplayName("카테고리 리스트에 해당하는 도서를 반환하는지 테스트")
    @Test
    void testGetBooksByCategoryIds() {

        // dummy data setting
        Category rootCategory = categoryRepository.save(Category.builder()
                .parentCategory(null)
                .categoryId(1)
                .categoryName("최상단 카테고리")
                .depth(1)
                .build());

        Category level1Category = categoryRepository.save(Category.builder()
                .parentCategory(rootCategory)
                .categoryId(2)
                .categoryName("1계층 카테고리")
                .depth(2)
                .build());

        Category level2Category = categoryRepository.save(Category.builder()
                .parentCategory(level1Category)
                .categoryId(3)
                .categoryName("2계층 카테고리1")
                .depth(3)
                .build());

        Category level2_1Category = categoryRepository.save(Category.builder()
                .parentCategory(level1Category)
                .categoryId(4)
                .categoryName("2계층 카테고리2")
                .depth(3)
                .build());

        Publisher publisher = publisherRepository.save(Publisher.builder()
                .publisherName("TestPublisherName")
                .publisherEmail("TestPublisheEmail@test.com")
                .build());

        // 최상단 카테고리에 해당하는 도서 세팅
        Book book1 = bookRepository.save(Book.builder()
                .bookName("TestBookNameInRootCategory")
                .bookIndex("TestBookIndex")
                .bookDesc("TestBookDesc")
                .bookIsbn("TestBookIsbn")
                .bookPrice(BigDecimal.valueOf(10000))
                .bookDiscount(BigDecimal.valueOf(20))
                .bookPackage(TableStatus.TRUE)
                .bookPublished(LocalDate.of(2024, Month.APRIL, 6))
                .bookStock(100)
                .bookAverageScore(4.5f)
                .bookLikeCount(500)
                .publisher(publisher)
                .build());

        bookCategoryRepository.save(
                BookCategory.builder()
                        .category(rootCategory)
                        .book(book1)
                        .build());

        bookThumbnailRepository.save(
                BookThumbnail.builder()
                        .book(book1)
                        .thumbnailImageUrl("TestTumbnailImageURl-TestBookInRootCategory")
                        .build());

        // 1계층 카테고리에 해당하는 도서 세팅
        bookCategoryRepository.save(
                BookCategory.builder()
                        .category(level1Category)
                        .book(bookRepository.save(Book.builder()
                                .bookName("TestBookNameIn1LevelCategory")
                                .bookIndex("TestBookIndex")
                                .bookDesc("TestBookDesc")
                                .bookIsbn("TestBookIsbn")
                                .bookPrice(BigDecimal.valueOf(10000))
                                .bookDiscount(BigDecimal.valueOf(20))
                                .bookPackage(TableStatus.TRUE)
                                .bookPublished(LocalDate.of(2024, Month.APRIL, 6))
                                .bookStock(100)
                                .bookAverageScore(4.5f)
                                .bookLikeCount(500)
                                .publisher(publisher)
                                .build())
                        )
                        .build());

        // 2계층 카테고리1에 해당하는 도서 세팅
        bookCategoryRepository.save(
                BookCategory.builder()
                        .category(level2Category)
                        .book(bookRepository.save(Book.builder()
                                .bookName("TestBookNameIn2LevelCategory1")
                                .bookIndex("TestBookIndex")
                                .bookDesc("TestBookDesc")
                                .bookIsbn("TestBookIsbn")
                                .bookPrice(BigDecimal.valueOf(10000))
                                .bookDiscount(BigDecimal.valueOf(20))
                                .bookPackage(TableStatus.TRUE)
                                .bookPublished(LocalDate.of(2024, Month.APRIL, 6))
                                .bookStock(100)
                                .bookAverageScore(4.5f)
                                .bookLikeCount(500)
                                .publisher(publisher)
                                .build())
                        )
                        .build());

        // 2계층 카테고리2에 해당하는 도서 세팅
        bookCategoryRepository.save(
                BookCategory.builder()
                        .category(level2_1Category)
                        .book(bookRepository.save(Book.builder()
                                .bookName("TestBookNameIn2LevelCategory2")
                                .bookIndex("TestBookIndex")
                                .bookDesc("TestBookDesc")
                                .bookIsbn("TestBookIsbn")
                                .bookPrice(BigDecimal.valueOf(10000))
                                .bookDiscount(BigDecimal.valueOf(20))
                                .bookPackage(TableStatus.TRUE)
                                .bookPublished(LocalDate.of(2024, Month.APRIL, 6))
                                .bookStock(100)
                                .bookAverageScore(4.5f)
                                .bookLikeCount(500)
                                .publisher(publisher)
                                .build())
                        )
                        .build());


        List<Integer> categoryList = List.of(
                rootCategory.getCategoryId(),
                level1Category.getCategoryId(),
                level2Category.getCategoryId(),
                level2_1Category.getCategoryId());

        Pageable pageable = PageRequest.of(0, 10, Sort.by("bookId").descending());

        Page<BookDetailResponse> bookList = bookCategoryRepository.getBooksByCategoryIds(categoryList, pageable);

        assertEquals(4, bookList.getContent().size());
        assertEquals("TestBookNameInRootCategory", bookList.getContent().get(0).getBookName());
        assertEquals("TestPublisherName", bookList.getContent().get(0).getPublisherName());
        assertEquals("TestTumbnailImageURl-TestBookInRootCategory", bookList.getContent().get(0).getThumbnailImageUrl());
        assertEquals(4, bookList.getTotalElements());
        assertEquals(1, bookList.getTotalPages());
        assertEquals(10, bookList.getSize());
    }
}
