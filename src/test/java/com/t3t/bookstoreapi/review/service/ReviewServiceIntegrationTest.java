package com.t3t.bookstoreapi.review.service;

import com.t3t.bookstoreapi.book.enums.TableStatus;
import com.t3t.bookstoreapi.book.exception.BookNotFoundException;
import com.t3t.bookstoreapi.book.model.entity.Book;
import com.t3t.bookstoreapi.book.repository.BookRepository;
import com.t3t.bookstoreapi.member.model.constant.MemberRole;
import com.t3t.bookstoreapi.member.model.constant.MemberStatus;
import com.t3t.bookstoreapi.member.model.entity.Member;
import com.t3t.bookstoreapi.member.model.entity.MemberGrade;
import com.t3t.bookstoreapi.member.model.entity.MemberGradePolicy;
import com.t3t.bookstoreapi.member.repository.MemberGradePolicyRepository;
import com.t3t.bookstoreapi.member.repository.MemberGradeRepository;
import com.t3t.bookstoreapi.member.repository.MemberRepository;
import com.t3t.bookstoreapi.model.response.PageResponse;
import com.t3t.bookstoreapi.publisher.model.entity.Publisher;
import com.t3t.bookstoreapi.publisher.repository.PublisherRepository;
import com.t3t.bookstoreapi.review.model.entity.Review;
import com.t3t.bookstoreapi.review.model.entity.ReviewImage;
import com.t3t.bookstoreapi.review.model.response.ReviewResponse;
import com.t3t.bookstoreapi.review.repository.ReviewImageRepository;
import com.t3t.bookstoreapi.review.repository.ReviewRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * {@link ReviewService} 클래스의 통합 테스트
 *
 * @author Yujin-nKim(김유진)
 */
@SpringBootTest
@Transactional
@ActiveProfiles("test")
class ReviewServiceIntegrationTest {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ReviewService reviewService;
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

            Review review = reviewRepository.save(Review.builder()
                    .reviewComment("ReviewComment"+i)
                    .reviewScore(5)
                    .reviewCreatedAt(LocalDateTime.now())
                    .reviewUpdatedAt(LocalDateTime.now())
                    .book(book)
                    .member(member)
                    .build());

            reviewImageRepository.save(ReviewImage.builder()
                    .review(review)
                    .reviewImageUrl("imageUrl")
                    .build());
        }

        entityManager.clear();

        Pageable pageable = PageRequest.of(0, 10, Sort.by("reviewId").descending());

        PageResponse<ReviewResponse> response = reviewService.findReviewsByBookId(book.getBookId(), pageable);

        assertEquals(5, response.getContent().size());
        assertEquals("test", response.getContent().get(0).getName());
        assertEquals("imageUrl", response.getContent().get(0).getReviewImgUrlList().get(0));
    }

    @DisplayName("도서별 리뷰 목록 조회 테스트 | 요청한 도서가 존재하지 않는 경우")
    @Test
    void testFindReviewsByBookId_NonExistingBook_ThrowException() {

        Long nonExistingBookId = 0L;
        Pageable pageable = PageRequest.of(0, 10);
        
        assertThrows(BookNotFoundException.class, () -> {
            reviewService.findReviewsByBookId(nonExistingBookId, pageable);
        });
    }
}