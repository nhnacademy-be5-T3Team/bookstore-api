package com.t3t.bookstoreapi.order.repository;

import com.t3t.bookstoreapi.book.enums.TableStatus;
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
import com.t3t.bookstoreapi.order.model.dto.OrderDetailDto;
import com.t3t.bookstoreapi.order.model.entity.*;
import com.t3t.bookstoreapi.publisher.model.entity.Publisher;
import com.t3t.bookstoreapi.publisher.repository.PublisherRepository;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class OrderDetailRepositoryTest {
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private PublisherRepository publisherRepository;
    @Autowired
    private PackagingRepository packagingRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private DeliveryRepository deliveryRepository;
    @Autowired
    private OrderStatusRepository orderStatusRepository;
    @Autowired
    private MemberGradePolicyRepository memberGradePolicyRepository;
    @Autowired
    private MemberGradeRepository memberGradeRepository;

    private Publisher testPublisher;
    private Book testBook;
    private Packaging testPackaging;
    private MemberGradePolicy testMemberPolicy;
    private MemberGrade testMemberGrade;
    private Member testMember;
    private Order testOrder;
    private OrderStatus testOrderStatus;
    private Delivery testDelivery;

    /**
     * 테스트 데이터 초기화
     *
     * @autor woody35545(구건모)
     */
    @BeforeEach
    void setUp() {
        testPublisher = publisherRepository.save(Publisher.builder()
                .publisherName("testPublisherName")
                .publisherEmail("test@mail.com")
                .build());

        testBook = bookRepository.save(Book.builder()
                .publisher(testPublisher)
                .bookName("testBookName")
                .bookIndex("testBookIndex")
                .bookDesc("testBookDesc")
                .bookIsbn("testBookIsbn")
                .bookPrice(new BigDecimal("10000"))
                .bookDiscount(new BigDecimal("20"))
                .bookPackage(TableStatus.TRUE)
                .bookPublished(LocalDate.of(2021, Month.JANUARY, 1))
                .bookStock(100)
                .bookAverageScore(4.5f)
                .bookLikeCount(500)
                .build());

        testPackaging = packagingRepository.save(Packaging.builder()
                .name("testPackaging")
                .price(BigDecimal.valueOf(1L)).build());

        testMemberPolicy = memberGradePolicyRepository.save(MemberGradePolicy.builder()
                .startAmount(new BigDecimal("0"))
                .endAmount(new BigDecimal("100000"))
                .build());

        testMemberGrade = memberGradeRepository.save(MemberGrade.builder()
                .policy(testMemberPolicy)
                .name("test")
                .build());

        testMember = memberRepository.save(Member.builder()
                .email("testEmail@mail.com")
                .name("testName")
                .latestLogin(LocalDateTime.now().withNano(0))
                .point(1000L)
                .phone("010-1234-5678")
                .status(MemberStatus.ACTIVE)
                .grade(testMemberGrade)
                .birthDate(LocalDate.now())
                .role(MemberRole.USER)
                .build());

        testDelivery = deliveryRepository.save(Delivery.builder()
                .price(new BigDecimal("3000"))
                .addressNumber(12345)
                .roadnameAddress("testRoadnameAddress")
                .detailAddress("testDetailAddress")
                .recipientName("testRecipientName")
                .recipientPhoneNumber("010-1234-5678")
                .deliveryDate(LocalDate.now())
                .build());


        testOrderStatus = orderStatusRepository.save(OrderStatus.builder()
                .name("testOrderStatus")
                .build());

        testOrder = orderRepository.save(Order.builder()
                .member(testMember)
                .orderDatetime(LocalDateTime.now().withNano(0))
                .delivery(testDelivery)
                .build());

    }

    /**
     * 주문 상세 식별자로 주문 상세 DTO 조회하는 QueryDSL 메서드 테스트
     *
     * @author woody35545(구건모)
     * @see OrderDetailRepositoryCustom#getOrderDetailDtoById(long)
     */
    @Test
    @DisplayName("주문 상세 식별자로 주문 상세 DTO 조회")
    void getOrderDetailDtoByIdTest() {
        // given
        final OrderDetail testOrderDetail = orderDetailRepository.save(
                OrderDetail.builder()
                        .book(testBook)
                        .packaging(testPackaging)
                        .order(testOrder)
                        .orderStatus(testOrderStatus)
                        .createdAt(LocalDateTime.now().withNano(0))
                        .quantity(1L)
                        .build());

        // when
        final Optional<OrderDetailDto> optOrderDetailDto = orderDetailRepository.getOrderDetailDtoById(testOrderDetail.getId());

        log.info("OrderDetailDto => {}", optOrderDetailDto.get());

        assertTrue(optOrderDetailDto.isPresent());
        assertEquals(testOrderDetail.getId(), optOrderDetailDto.get().getId());
        assertEquals(testOrderDetail.getQuantity(), optOrderDetailDto.get().getQuantity());
        assertEquals(testOrderDetail.getCreatedAt(), optOrderDetailDto.get().getCreatedAt());
        assertEquals(testOrderDetail.getOrder().getId(), optOrderDetailDto.get().getOrderId());
        assertEquals(testOrderDetail.getBook().getBookId(), optOrderDetailDto.get().getBookId());
        assertEquals(testOrderDetail.getBook().getBookName(), optOrderDetailDto.get().getBookName());
        assertEquals(testOrderDetail.getBook().getPublisher().getPublisherName(), optOrderDetailDto.get().getBookPublisherName());
        assertEquals(testOrderDetail.getBook().getBookPrice(), optOrderDetailDto.get().getBookPrice());
        assertEquals(testOrderDetail.getBook().getBookDiscount(), optOrderDetailDto.get().getBookDiscount());
        assertEquals(testOrderDetail.getPackaging().getName(), optOrderDetailDto.get().getPackagingName());
        assertEquals(testOrderDetail.getPackaging().getPrice(), optOrderDetailDto.get().getPackagingPrice());
        assertEquals(testOrderDetail.getOrderStatus().getName(), optOrderDetailDto.get().getOrderStatusName());

    }
}
