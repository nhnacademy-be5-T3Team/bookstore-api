package com.t3t.bookstoreapi.book.repository;

import com.t3t.bookstoreapi.book.enums.TableStatus;
import com.t3t.bookstoreapi.book.model.dto.ParticipantRoleRegistrationDtoByBookId;
import com.t3t.bookstoreapi.book.model.entity.*;
import com.t3t.bookstoreapi.book.model.response.BookDetailResponse;
import com.t3t.bookstoreapi.book.repository.impl.BookRepositoryCustomImpl;
import com.t3t.bookstoreapi.category.model.entity.Category;
import com.t3t.bookstoreapi.category.repository.CategoryRepository;
import com.t3t.bookstoreapi.config.DataSourceConfig;
import com.t3t.bookstoreapi.config.DatabasePropertiesConfig;
import com.t3t.bookstoreapi.config.QueryDslConfig;
import com.t3t.bookstoreapi.config.RestTemplateConfig;
import com.t3t.bookstoreapi.keymanager.service.SecretKeyManagerService;
import com.t3t.bookstoreapi.participant.model.entity.Participant;
import com.t3t.bookstoreapi.participant.model.entity.ParticipantRole;
import com.t3t.bookstoreapi.participant.repository.ParticipantRepository;
import com.t3t.bookstoreapi.participant.repository.ParticipantRoleRepository;
import com.t3t.bookstoreapi.property.SecretKeyManagerProperties;
import com.t3t.bookstoreapi.property.SecretKeyProperties;
import com.t3t.bookstoreapi.publisher.model.entity.Publisher;
import com.t3t.bookstoreapi.publisher.repository.PublisherRepository;
import com.t3t.bookstoreapi.recommendation.model.response.BookInfoBriefResponse;
import com.t3t.bookstoreapi.tag.model.entity.Tag;
import com.t3t.bookstoreapi.tag.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * {@link BookRepositoryCustomImpl} 클래스의 단위 테스트
 *
 * @author Yujin-nKim(김유진)
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({DataSourceConfig.class, DatabasePropertiesConfig.class,
        QueryDslConfig.class, RestTemplateConfig.class,
        SecretKeyManagerService.class, SecretKeyManagerProperties.class, SecretKeyProperties.class})
@ActiveProfiles("test")
class BookRepositoryCustomImplUnitTest {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookThumbnailRepository bookThumbnailRepository;
    @Autowired
    private PublisherRepository publisherRepository;
    @Autowired
    private ParticipantRepository participantRepository;
    @Autowired
    private ParticipantRoleRepository roleRepository;
    @Autowired
    private ParticipantRoleRegistrationRepository registrationRepository;
    @Autowired
    private BookTagRepository bookTagRepository;
    @Autowired
    private BookCategoryRepository bookCategoryRepository;
    @Autowired
    private BookImageRepository bookImageRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    void init() {
        assertEquals(0, bookRepository.findAll().size(), "데이터베이스에 도서 데이터가 존재합니다.");
    }
    @Disabled
    @DisplayName("도서 식별자로 도서 상세 내역 조회 테스트")
    @Test
    void testGetBookDetailsById(){

        // 더미 데이터 setting
        Publisher publisher = publisherRepository.save(Publisher.builder()
                .publisherName("TestPublisherName")
                .publisherEmail("TestPublisheEmail@test.com")
                .build());

        Book book = bookRepository.save(Book.builder()
                .bookName("TestBookName")
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
                .isDeleted(TableStatus.FALSE)
                .build());

        BookThumbnail thumbnail = bookThumbnailRepository.save(BookThumbnail.builder()
                .book(book)
                .thumbnailImageUrl("TestThumbnailImageUrl")
                .isDeleted(TableStatus.FALSE)
                .build());

        for(int i = 0; i < 3; i++) {
            bookImageRepository.save(BookImage.builder()
                            .book(book)
                            .bookImageUrl("TestBookImageUrl" + i)
                            .isDeleted(TableStatus.FALSE)
                            .build());

            Tag tag = tagRepository.save(Tag.builder()
                    .tagName("TestTag" + i)
                    .build());

            bookTagRepository.save(BookTag.builder()
                    .book(book)
                    .tag(tag)
                    .isDeleted(TableStatus.FALSE)
                    .build());

            Category category = categoryRepository.save(Category.builder()
                    .categoryName("TestCategoryName" + i)
                    .depth(1)
                    .build());

            bookCategoryRepository.save(BookCategory.builder()
                    .book(book)
                    .category(category)
                    .isDeleted(TableStatus.FALSE)
                    .build());

            Participant participant = participantRepository.save(Participant.builder()
                    .participantName("TestParticipantName" + i)
                    .participantEmail("TestParticipantEmail@test.com" + i)
                    .build());

            ParticipantRole participantRole = roleRepository.save(ParticipantRole.builder()
                    .participantRoleNameKr("participantRoleNameKr"+i)
                    .participantRoleNameEn("participantRoleNameEn"+i)
                    .build());

            registrationRepository.save(ParticipantRoleRegistration.builder()
                    .book(book)
                    .participant(participant)
                    .participantRole(participantRole)
                    .isDeleted(TableStatus.FALSE)
                    .build());
        }

        BookDetailResponse bookDetails = bookRepository.getBookDetailsById(book.getBookId());

        assertEquals(bookDetails.getBookName(), "TestBookName");
        assertEquals(bookDetails.getPublisherName(), "TestPublisherName");
        assertEquals(bookDetails.getThumbnailImageUrl(), "TestThumbnailImageUrl");
        assertEquals(bookDetails.getBookImageUrlList().size(), 3);
        assertEquals(bookDetails.getCategoryList().size(), 3);
        assertEquals(bookDetails.getCategoryList().get(0).getName(), "TestCategoryName0");
        assertEquals(bookDetails.getTagList().size(), 3);
        assertEquals(bookDetails.getTagList().get(0).getName(), "TestTag0");
        assertEquals(bookDetails.getParticipantList().size(), 3);
        assertEquals(bookDetails.getParticipantList().get(0).getName(), "TestParticipantName0");

    }
    @Disabled
    @DisplayName("도서 식별자 리스트로 도서 참여자 정보 조회 테스트")
    @Test
    void testGetBookParticipantDtoListByIdList() {

        // dummy data setting
        List<Long> dummybookIdList = new ArrayList<>();

        for(int i = 1; i < 4; i++) {
            Publisher publisher = publisherRepository.save(Publisher.builder()
                    .publisherName("TestPublisherName" + i)
                    .publisherEmail("TestPublisheEmail@test.com")
                    .build());

            Book book = bookRepository.save(Book.builder()
                    .bookName("TestBookName" + i)
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
                    .isDeleted(TableStatus.FALSE)
                    .build());

            dummybookIdList.add(book.getBookId());

            for(int j = 0; j < i; j++) {
                Participant participant = participantRepository.save(Participant.builder()
                        .participantName("TestParticipantName" + i)
                        .participantEmail("TestParticipantEmail@test.com" + i)
                        .build());

                ParticipantRole participantRole = roleRepository.save(ParticipantRole.builder()
                        .participantRoleNameKr("participantRoleNameKr"+i)
                        .participantRoleNameEn("participantRoleNameEn"+i)
                        .build());

                registrationRepository.save(ParticipantRoleRegistration.builder()
                        .book(book)
                        .participant(participant)
                        .participantRole(participantRole)
                        .isDeleted(TableStatus.FALSE)
                        .build());
            }
        }

        List<ParticipantRoleRegistrationDtoByBookId> result =  bookRepository.getBookParticipantDtoListByIdList(dummybookIdList);

        assertEquals(dummybookIdList.size(), result.size());
        assertEquals(1, result.get(0).getParticipantList().size());
        assertEquals(2, result.get(1).getParticipantList().size());
        assertEquals(3, result.get(2).getParticipantList().size());
        assertEquals("TestParticipantName1", result.get(0).getParticipantList().get(0).getName());
    }
    @Disabled
    @DisplayName("특정 날짜를 기준으로 7일 이내에 출판된 도서 목록을 반환하는지 테스트")
    @Test
    void testGetRecentlyPublishedBooks() {

        LocalDate testDate = LocalDate.of(2024, 4, 22);

        // dummy data setting
        Publisher publisher = publisherRepository.save(Publisher.builder()
                .publisherName("TestPublisherName")
                .publisherEmail("TestPublisheEmail@test.com")
                .build());

        Random random = new Random();

        List<Book> recentBooks = new ArrayList<>();
        List<Book> pastBooks = new ArrayList<>();
        List<Book> futureBooks = new ArrayList<>();

        // 7일 이내 도서 20개 생성
        for (int i = 0; i < 20; i++) {
            int randomDays = random.nextInt(7);

            Book book = bookRepository.save(Book.builder()
                    .bookName("RecentBook" + i)
                    .bookIndex("TestBookIndex")
                    .bookDesc("TestBookDesc")
                    .bookIsbn("TestBookIsbn")
                    .bookPrice(BigDecimal.valueOf(10000))
                    .bookDiscount(BigDecimal.valueOf(20))
                    .bookPackage(TableStatus.TRUE)
                    .bookPublished(testDate.minusDays(randomDays))
                    .bookStock(100)
                    .bookAverageScore(4.5f)
                    .bookLikeCount(500)
                    .publisher(publisher)
                    .isDeleted(TableStatus.FALSE)
                    .build());
            bookThumbnailRepository.save(BookThumbnail.builder()
                    .book(book)
                    .thumbnailImageUrl("TestThumbnailImageUrl"+i)
                    .isDeleted(TableStatus.FALSE)
                    .build());
            recentBooks.add(book);
        }
        // 7일 이전 도서 3개 생성
        for (int i = 1; i < 4; i++) {
            Book book = bookRepository.save(Book.builder()
                    .bookName("PastBook" + i)
                    .bookIndex("TestBookIndex")
                    .bookDesc("TestBookDesc")
                    .bookIsbn("TestBookIsbn")
                    .bookPrice(BigDecimal.valueOf(10000))
                    .bookDiscount(BigDecimal.valueOf(20))
                    .bookPackage(TableStatus.TRUE)
                    .bookPublished(testDate.minusDays(i * 30)) // testDate을 기준으로 i달씩 이전
                    .bookStock(100)
                    .bookAverageScore(4.5f)
                    .bookLikeCount(500)
                    .publisher(publisher)
                    .isDeleted(TableStatus.FALSE)
                    .build());
            pastBooks.add(book);
        }

        // testDate 이후 도서 2개 생성
        for (int i = 1; i < 3; i++) {
            Book book = bookRepository.save(Book.builder()
                    .bookName("PastBook" + i)
                    .bookIndex("TestBookIndex")
                    .bookDesc("TestBookDesc")
                    .bookIsbn("TestBookIsbn")
                    .bookPrice(BigDecimal.valueOf(10000))
                    .bookDiscount(BigDecimal.valueOf(20))
                    .bookPackage(TableStatus.TRUE)
                    .bookPublished(testDate.plusDays(i)) // testDate을 기준으로 i달씩 이전
                    .bookStock(100)
                    .bookAverageScore(4.5f)
                    .bookLikeCount(500)
                    .publisher(publisher)
                    .isDeleted(TableStatus.FALSE)
                    .build());
            futureBooks.add(book);
        }


        List<BookInfoBriefResponse> result = bookRepository.getRecentlyPublishedBooks(testDate, 10);

        assertEquals(10, result.size());
        assertEquals("TestThumbnailImageUrl0", result.get(0).getThumbnailImageUrl());
        assertFalse(result.contains(pastBooks));
        assertFalse(result.contains(futureBooks));
    }

    @DisplayName("도서의 좋아요 수와 평점을 기준으로 하는 도서 목록 조회 테스트")
    @Test
    void testGetBooksByMostLikedAndHighAverageScore() {
        // dummy data setting
        Publisher publisher = publisherRepository.save(Publisher.builder()
                .publisherName("TestPublisherName")
                .publisherEmail("TestPublisheEmail@test.com")
                .build());

        List<Book> bookList = new ArrayList<>();

        for(int i = 1; i <= 20; i++) {
            Book book = bookRepository.save(Book.builder()
                    .bookId((long) i)
                    .bookName("TestBook" + i)
                    .bookIndex("TestBookIndex")
                    .bookDesc("TestBookDesc")
                    .bookIsbn("TestBookIsbn")
                    .bookPrice(BigDecimal.valueOf(10000))
                    .bookDiscount(BigDecimal.valueOf(20))
                    .bookPackage(TableStatus.TRUE)
                    .bookPublished(LocalDate.of(2024, Month.APRIL, 6))
                    .bookStock(100)
                    .bookAverageScore(5.0f - i*0.1f)
                    .bookLikeCount(500 - i)
                    .publisher(publisher)
                    .isDeleted(TableStatus.FALSE)
                    .build());
            bookList.add(book);
        }

        List<BookInfoBriefResponse> result = bookRepository.getBooksByMostLikedAndHighAverageScore(10);

        assertEquals(10, result.size());
        assertEquals(bookList.get(0).getBookId(), result.get(0).getId());
        assertEquals(bookList.get(9).getBookId(), result.get(9).getId());
    }
}
