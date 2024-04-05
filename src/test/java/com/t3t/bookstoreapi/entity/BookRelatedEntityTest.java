package com.t3t.bookstoreapi.entity;

import com.t3t.bookstoreapi.book.model.entity.*;
import com.t3t.bookstoreapi.book.repository.*;
import com.t3t.bookstoreapi.booklike.model.entity.BookLike;
import com.t3t.bookstoreapi.booklike.repository.BookLikeRepository;
import com.t3t.bookstoreapi.category.model.entity.Category;
import com.t3t.bookstoreapi.category.repository.CategoryRepository;
import com.t3t.bookstoreapi.member.domain.Member;
import com.t3t.bookstoreapi.member.domain.MemberGrade;
import com.t3t.bookstoreapi.member.domain.MemberGradePolicy;
import com.t3t.bookstoreapi.member.repository.MemberGradePolicyRepository;
import com.t3t.bookstoreapi.member.repository.MemberGradeRepository;
import com.t3t.bookstoreapi.member.repository.MemberRepository;
import com.t3t.bookstoreapi.participant.model.entity.Participant;
import com.t3t.bookstoreapi.participant.model.entity.ParticipantRole;
import com.t3t.bookstoreapi.participant.repository.ParticipantRepository;
import com.t3t.bookstoreapi.participant.repository.ParticipantRoleRepository;
import com.t3t.bookstoreapi.publisher.model.entity.Publisher;
import com.t3t.bookstoreapi.publisher.repository.PublisherRepository;
import com.t3t.bookstoreapi.review.model.entity.Review;
import com.t3t.bookstoreapi.review.model.entity.ReviewImage;
import com.t3t.bookstoreapi.review.repository.ReviewImageRepository;
import com.t3t.bookstoreapi.review.repository.ReviewRepository;
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
import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class BookRelatedEntityTest {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private PublisherRepository publisherRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ParticipantRepository participantRepository;
    @Autowired
    private ParticipantRoleRepository participantRoleRepository;
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
    @Autowired
    private BookLikeRepository bookLikeRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ReviewImageRepository reviewImageRepository;
    @Autowired
    private ParticipantRoleRegistrationRepository participantRoleRegis;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberGradePolicyRepository memberGradePolicyRepository;
    @Autowired
    private MemberGradeRepository memberGradeRepository;

    private Book testBook;
    private Member testUser;

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

    @BeforeEach
    void setUpMemberEntity() {
        MemberGradePolicy memberGradePolicy = memberGradePolicyRepository.save(MemberGradePolicy.builder()
                .startAmount(BigDecimal.valueOf(0))
                .endAmount(BigDecimal.valueOf(100000))
                .build());

        MemberGrade memberGrade = memberGradeRepository.save(MemberGrade.builder().gradeId(2)
                .policy(memberGradePolicy) // MemberGrade 테이블 autoincrement 적용 후 없애기
                .name("test")
                .build());

        testUser = memberRepository.save(Member.builder()
                .name("test")
                .email("woody@mail.com")
                .point(1000L)
                .phone("010-1234-5678")
                .latestLogin(LocalDateTime.now())
                .birthDate(LocalDateTime.now().toLocalDate())
                .gradeId(memberGrade)
                .status("ACTIVE")
                .role(1)
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

        BookCategory savedBookCategory = bookCategoryRepository.findById(bookCategory.getBookCategoryId()).orElse(null);

        assertNotNull(savedBookCategory);
        assertEquals("categoryName", savedBookCategory.getCategory().getCategoryName());
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

        BookTag savedBookTag = bookTagRepository.findById(bookTag.getBookTagId()).orElse(null);

        assertNotNull(savedBookTag);
        assertEquals("TestTagName", savedBookTag.getTag().getTagName());
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

    @Test
    @DisplayName("ParticipantRoleRegistration entity 맵핑 테스트")
    void testParticipantRoleRegistrationMapping() {


        Participant participant = participantRepository.save(Participant.builder()
                .participantName("TestParticipantName")
                .participantEmail("TestParticipantEmail@example.com")
                .build());

        ParticipantRole participantRole = participantRoleRepository.save(ParticipantRole.builder()
                        .participantRoleNameEn("TestParticipantRoleNameEn")
                        .participantRoleNameKr("TestParticipantRoleNameKr")
                        .build());

        ParticipantRoleRegistration participantRoleRegistration = ParticipantRoleRegistration.builder()
                .book(testBook)
                .participant(participant)
                .participantRole(participantRole)
                .build();

        participantRoleRegis.save(participantRoleRegistration);

        ParticipantRoleRegistration saved = participantRoleRegis.findById(participantRoleRegistration.getParticipantRoleRegistrationId()).orElse(null);

        assertNotNull(saved);
        assertEquals(participant.getParticipantName(), participantRoleRegistration.getParticipant().getParticipantName());

    }

    @Test
    @DisplayName("BookLike entity 맵핑 테스트")
    void testBookLikeEntityMapping() {

        BookLike bookLike = BookLike.builder().book(testBook).member(testUser).build();

        bookLikeRepository.save(bookLike);

        BookLike savedBookLike = bookLikeRepository.findById(bookLike.getId()).orElse(null);

        assertNotNull(savedBookLike);
        assertEquals(testBook.getBookIsbn(), savedBookLike.getId().getBook().getBookIsbn());
    }

    @Test
    @DisplayName("Review entity 맵핑 테스트")
    void testReviewEntityMapping() {
        Review review = Review.builder()
                .book(testBook)
                .member(testUser)
                .reviewComment("review 내용")
                .reviewScore(3)
                .reviewCreatedAt(LocalDateTime.now())
                .reviewUpdatedAt(LocalDateTime.now())
                .build();

        reviewRepository.save(review);

        Review savedReview = reviewRepository.findById(review.getReviewId()).orElse(null);

        assertNotNull(savedReview);
        assertEquals(review.getReviewComment(), savedReview.getReviewComment());
    }

    @Test
    @DisplayName("Review Image entity 맵핑 텍스트")
    void testReviewImageEntityMapping() {
        Review review = reviewRepository.save(Review.builder()
                .book(testBook)
                .member(testUser)
                .reviewComment("review 내용")
                .reviewScore(3)
                .reviewCreatedAt(LocalDateTime.now())
                .reviewUpdatedAt(LocalDateTime.now())
                .build());

        ReviewImage reviewImage = ReviewImage.builder().reviewImageUrl("img_url").review(review).build();

        reviewImageRepository.save(reviewImage);

        ReviewImage savedReviewImage = reviewImageRepository.findById(reviewImage.getReviewImageId()).orElse(null);

        assertNotNull(savedReviewImage);
        assertEquals(reviewImage.getReviewImageUrl(), savedReviewImage.getReviewImageUrl());
    }
}
