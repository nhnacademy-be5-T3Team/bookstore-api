package com.t3t.bookstoreapi.order.repository;

import com.t3t.bookstoreapi.book.enums.TableStatus;
import com.t3t.bookstoreapi.book.model.entity.Book;
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
import com.t3t.bookstoreapi.order.model.dto.OrderDetailDto;
import com.t3t.bookstoreapi.order.model.entity.*;
import com.t3t.bookstoreapi.property.SecretKeyManagerProperties;
import com.t3t.bookstoreapi.property.SecretKeyProperties;
import com.t3t.bookstoreapi.publisher.model.entity.Publisher;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Import({DataSourceConfig.class, DatabasePropertiesConfig.class,
        QueryDslConfig.class, RestTemplateConfig.class,
        SecretKeyManagerService.class, SecretKeyManagerProperties.class, SecretKeyProperties.class})
public class OrderDetailRepositoryTest {
    @Autowired
    private TestEntityManager em;
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    /**
     * OrderDetail 의 의존 관계 엔티티
     */
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
        testPublisher = em.persist(Publisher.builder()
                .publisherName("testPublisherName")
                .publisherEmail("test@mail.com")
                .build());

        testBook = em.persist(Book.builder()
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

        testPackaging = em.persist(Packaging.builder()
                .name("testPackaging")
                .price(BigDecimal.valueOf(1L)).build());

        testMemberPolicy = em.persist(MemberGradePolicy.builder()
                .startAmount(new BigDecimal("0"))
                .endAmount(new BigDecimal("100000"))
                .build());

        testMemberGrade = em.persist(MemberGrade.builder()
                .policy(testMemberPolicy)
                .name("test")
                .build());

        testMember = em.persist(Member.builder()
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

        testDelivery = em.persist(Delivery.builder()
                .price(new BigDecimal("3000"))
                .addressNumber(12345)
                .roadnameAddress("testRoadnameAddress")
                .detailAddress("testDetailAddress")
                .recipientName("testRecipientName")
                .recipientPhoneNumber("010-1234-5678")
                .deliveryDate(LocalDate.now())
                .build());

        testOrderStatus = em.persist(OrderStatus.builder()
                .name("testOrderStatus")
                .build());

        testOrder = em.persist(Order.builder()
                .member(testMember)
                .createdAt(LocalDateTime.now().withNano(0))
                .delivery(testDelivery)
                .build());

        em.flush();
        em.clear();
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
                        .quantity(1)
                        .price(testBook.getBookPrice().subtract(testBook.getBookPrice()
                                .multiply(testBook.getBookDiscount().divide(new BigDecimal("100")))))
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
//        assertEquals(testOrderDetail.getBook().getBookPrice(), optOrderDetailDto.get().getBookPrice());
//        assertEquals(testOrderDetail.getBook().getBookDiscount(), optOrderDetailDto.get().getBookDiscount());
        assertEquals(testOrderDetail.getPackaging().getName(), optOrderDetailDto.get().getPackagingName());
        assertEquals(testOrderDetail.getPackaging().getPrice(), optOrderDetailDto.get().getPackagingPrice());
        assertEquals(testOrderDetail.getOrderStatus().getName(), optOrderDetailDto.get().getOrderStatusName());
    }


    /**
     * 주문 식별자로 주문 상세 조회 DTO 리스트 조회
     *
     * @author woody35545(구건모)
     * @see OrderDetailRepositoryCustom#getOrderDetailDtoListByOrderId(long)
     */
    @Test
    @DisplayName("주문 식별자로 주문 상세 DTO 리스트 조회")
    void getOrderDetailDtoListByOrderIdTest() {
        // given
        final List<OrderDetail> testOrderDetailList =
                List.of(
                        orderDetailRepository.save(
                                OrderDetail.builder()
                                        .book(testBook)
                                        .packaging(testPackaging)
                                        .order(testOrder)
                                        .orderStatus(testOrderStatus)
                                        .createdAt(LocalDateTime.now().withNano(0))
                                        .quantity(1)
                                        .price(testBook.getBookPrice().subtract(testBook.getBookPrice()
                                                .multiply(testBook.getBookDiscount().divide(new BigDecimal("100")))))
                                        .build()
                        ),
                        orderDetailRepository.save(
                                OrderDetail.builder()
                                        .book(testBook)
                                        .packaging(testPackaging)
                                        .order(testOrder)
                                        .orderStatus(testOrderStatus)
                                        .createdAt(LocalDateTime.now().withNano(0))
                                        .quantity(2)
                                        .price(testBook.getBookPrice().subtract(testBook.getBookPrice()
                                                .multiply(testBook.getBookDiscount().divide(new BigDecimal("100")))))
                                        .build()
                        )
                );


        // when
        final List<OrderDetailDto> orderDetailDtoList = orderDetailRepository.getOrderDetailDtoListByOrderId(testOrder.getId());

        orderDetailDtoList.sort((o1, o2) -> (int) (o1.getId() - o2.getId()));


        // then
        assertEquals(testOrderDetailList.size(), orderDetailDtoList.size());
        for (int i = 0; i < testOrderDetailList.size(); i++) {
            log.info("OrderDetailDto => {}", orderDetailDtoList.get(i));
            assertEquals(testOrderDetailList.get(i).getId(), orderDetailDtoList.get(i).getId());
            assertEquals(testOrderDetailList.get(i).getQuantity(), orderDetailDtoList.get(i).getQuantity());
            assertEquals(testOrderDetailList.get(i).getCreatedAt(), orderDetailDtoList.get(i).getCreatedAt());
            assertEquals(testOrderDetailList.get(i).getOrder().getId(), orderDetailDtoList.get(i).getOrderId());
            assertEquals(testOrderDetailList.get(i).getBook().getBookId(), orderDetailDtoList.get(i).getBookId());
            assertEquals(testOrderDetailList.get(i).getBook().getBookName(), orderDetailDtoList.get(i).getBookName());
            assertEquals(testOrderDetailList.get(i).getBook().getPublisher().getPublisherName(), orderDetailDtoList.get(i).getBookPublisherName());
//            assertEquals(testOrderDetailList.get(i).getBook().getBookPrice(), orderDetailDtoList.get(i).getBookPrice());
//            assertEquals(testOrderDetailList.get(i).getBook().getBookDiscount(), orderDetailDtoList.get(i).getBookDiscount());
            assertEquals(testOrderDetailList.get(i).getPackaging().getName(), orderDetailDtoList.get(i).getPackagingName());
            assertEquals(testOrderDetailList.get(i).getPackaging().getPrice(), orderDetailDtoList.get(i).getPackagingPrice());
            assertEquals(testOrderDetailList.get(i).getOrderStatus().getName(), orderDetailDtoList.get(i).getOrderStatusName());
        }
    }

    /**
     * 주문 식별자로 주문 상세 엔티티 리스트를 조회하는 QueryDSL 메서드 테스트
     *
     * @author woody35545(구건모)
     * @see OrderDetailRepositoryCustom#getOrderDetailListByOrderId(long)
     */
    @Test
    @DisplayName("주문 식별자로 주문 상세 엔티티 리스트 조회")
    void getOrderDetailListByOrderIdTest() {
        //given
        final List<OrderDetail> testOrderDetailList =
                List.of(orderDetailRepository.save(OrderDetail.builder()
                                .book(testBook)
                                .packaging(testPackaging)
                                .order(testOrder)
                                .orderStatus(testOrderStatus)
                                .createdAt(LocalDateTime.now().withNano(0))
                                .quantity(1)
                                .price(testBook.getBookPrice().subtract(testBook.getBookPrice()
                                        .multiply(testBook.getBookDiscount().divide(new BigDecimal("100")))))
                                .build()),
                        orderDetailRepository.save(OrderDetail.builder()
                                .book(testBook)
                                .packaging(testPackaging)
                                .order(testOrder)
                                .orderStatus(testOrderStatus)
                                .createdAt(LocalDateTime.now().withNano(0))
                                .quantity(2)
                                .price(testBook.getBookPrice().subtract(testBook.getBookPrice()
                                        .multiply(testBook.getBookDiscount().divide(new BigDecimal("100")))))
                                .build()));

        em.clear();

        // when
        List<OrderDetail> orderDetailList = orderDetailRepository.getOrderDetailListByOrderId(testOrder.getId());
        orderDetailList.sort((o1, o2) -> (int) (o1.getId() - o2.getId()));

        // then
        assertEquals(testOrderDetailList.size(), orderDetailList.size());
        for (int i = 0; i < testOrderDetailList.size(); i++) {

            assertEquals(testOrderDetailList.get(i).getId(), orderDetailList.get(i).getId());
            assertEquals(testOrderDetailList.get(i).getQuantity(), orderDetailList.get(i).getQuantity());
            assertEquals(testOrderDetailList.get(i).getCreatedAt(), orderDetailList.get(i).getCreatedAt());
            assertEquals(testOrderDetailList.get(i).getOrder().getId(), orderDetailList.get(i).getOrder().getId());
            assertEquals(testOrderDetailList.get(i).getBook().getBookId(), orderDetailList.get(i).getBook().getBookId());
            assertEquals(testOrderDetailList.get(i).getPackaging().getId(), orderDetailList.get(i).getPackaging().getId());
            assertEquals(testOrderDetailList.get(i).getOrderStatus().getId(), orderDetailList.get(i).getOrderStatus().getId());
        }

    }
}
