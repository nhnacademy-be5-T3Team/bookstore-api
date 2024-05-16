package com.t3t.bookstoreapi.review.repository;

import com.t3t.bookstoreapi.book.enums.TableStatus;
import com.t3t.bookstoreapi.book.model.entity.Book;
import com.t3t.bookstoreapi.book.repository.BookRepository;
import com.t3t.bookstoreapi.review.repository.impl.ReviewRepositoryCustomImpl;
import com.t3t.bookstoreapi.config.DataSourceConfig;
import com.t3t.bookstoreapi.config.DatabasePropertiesConfig;
import com.t3t.bookstoreapi.config.QueryDslConfig;
import com.t3t.bookstoreapi.config.RestTemplateConfig;
import com.t3t.bookstoreapi.keymanager.service.SecretKeyManagerService;
import com.t3t.bookstoreapi.member.model.constant.MemberRole;
import com.t3t.bookstoreapi.member.model.constant.MemberStatus;
import com.t3t.bookstoreapi.member.model.entity.Member;
import com.t3t.bookstoreapi.member.model.entity.MemberGrade;
import com.t3t.bookstoreapi.member.model.entity.MemberGradePolicy;
import com.t3t.bookstoreapi.member.repository.MemberGradePolicyRepository;
import com.t3t.bookstoreapi.member.repository.MemberGradeRepository;
import com.t3t.bookstoreapi.member.repository.MemberRepository;
import com.t3t.bookstoreapi.property.SecretKeyManagerProperties;
import com.t3t.bookstoreapi.property.SecretKeyProperties;
import com.t3t.bookstoreapi.publisher.model.entity.Publisher;
import com.t3t.bookstoreapi.publisher.repository.PublisherRepository;
import com.t3t.bookstoreapi.review.model.entity.Review;
import com.t3t.bookstoreapi.review.model.entity.ReviewImage;
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
import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * {@link ReviewRepositoryCustomImpl} 클래스의 단위 테스트
 * @author Yujin-nKim(김유진)
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({DataSourceConfig.class, DatabasePropertiesConfig.class,
        QueryDslConfig.class, RestTemplateConfig.class,
        SecretKeyManagerService.class, SecretKeyManagerProperties.class, SecretKeyProperties.class})
@ActiveProfiles("test")
class ReviewRepositoryCustomImplUnitTest {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ReviewImageRepository reviewImageRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private PublisherRepository publisherRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberGradeRepository memberGradeRepository;
    @Autowired
    private MemberGradePolicyRepository memberGradePolicyRepository;

    @DisplayName("도서별 리뷰 목록 조회 테스트")
    @Test
    void testFindReviewsByBookId() {
        // 더미 데이터 setting
        MemberGradePolicy memberGradePolicy = memberGradePolicyRepository.save(MemberGradePolicy.builder()
                .startAmount(BigDecimal.valueOf(0))
                .endAmount(BigDecimal.valueOf(100000))
                .build());

        MemberGrade memberGrade = memberGradeRepository.save(MemberGrade.builder()
                .policy(memberGradePolicy)
                .name("test")
                .build());

        Member member = memberRepository.save(Member.builder()
                .name("test")
                .email("woody@mail.com")
                .point(1000L)
                .phone("010-1234-5678")
                .latestLogin(LocalDateTime.now())
                .birthDate(LocalDateTime.now().toLocalDate())
                .grade(memberGrade)
                .status(MemberStatus.ACTIVE)
                .role(MemberRole.USER)
                .build());

        Publisher publisher = publisherRepository.save(Publisher.builder()
                .publisherName("TestPublisherName")
                .publisherEmail("TestPublisheEmail@test.com")
                .build());

        Book book = bookRepository.save(Book.builder()
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
                .isDeleted(TableStatus.FALSE)
                .build());

        for(int i = 0; i < 5; i++) {
            Review review = reviewRepository.save(Review.builder()
                    .reviewComment("ReviewComment" + i)
                    .reviewScore(5)
                    .reviewCreatedAt(LocalDateTime.now())
                    .reviewUpdatedAt(LocalDateTime.now())
                    .book(book)
                    .member(member)
                    .build());

            reviewImageRepository.save(ReviewImage.builder()
                    .review(review)
                    .reviewImageUrl("imageUrl" + i)
                    .build());
        }

        Pageable pageable = PageRequest.of(0, 10, Sort.by("reviewId").descending());

        Page<Review> response = reviewRepository.findReviewsByBookId(book.getBookId(), pageable);

        assertEquals(5, response.getContent().size());
        assertEquals(member.getName(), response.getContent().get(0).getMember().getName());
    }
}