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
import com.t3t.bookstoreapi.tag.model.entity.Tag;
import com.t3t.bookstoreapi.tag.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

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
                .build());

        BookThumbnail thumbnail = bookThumbnailRepository.save(BookThumbnail.builder()
                .book(book)
                .thumbnailImageUrl("TestThumbnailImageUrl")
                .build());

        for(int i = 0; i < 3; i++) {
            bookImageRepository.save(BookImage.builder()
                            .book(book)
                            .bookImageUrl("TestBookImageUrl" + i)
                            .build());

            Tag tag = tagRepository.save(Tag.builder()
                    .tagName("TestTag" + i)
                    .build());

            bookTagRepository.save(BookTag.builder()
                    .book(book)
                    .tag(tag)
                    .build());

            Category category = categoryRepository.save(Category.builder()
                    .categoryName("TestCategoryName" + i)
                    .depth(1)
                    .build());

            bookCategoryRepository.save(BookCategory.builder()
                    .book(book)
                    .category(category)
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
}
