package com.t3t.bookstoreapi.order.model.entity;

import com.netflix.discovery.converters.Auto;
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
import com.t3t.bookstoreapi.order.repository.GuestOrderRepository;
import com.t3t.bookstoreapi.order.repository.OrderStatusRepository;
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
public class GuestOrderTest {
    @Autowired
    private TestEntityManager em;
    @Autowired
    private GuestOrderRepository guestOrderRepository;

    private Publisher testPublisher;
    private MemberGradePolicy testMemberPolicy;
    private MemberGrade testMemberGrade;
    private Member testMember;
    private Order testOrder;
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

        testOrder = em.persist(Order.builder()
                .member(testMember)
                .createdAt(LocalDateTime.now().withNano(0))
                .delivery(testDelivery)
                .build());

        em.flush();
        em.clear();
    }

    /**
     * GuestOrder 엔티티 맵핑 테스트
     *
     * @autor woody35545(구건모)
     */
    @Test
    @DisplayName("GuestOrder 엔티티 맵핑 테스트")
    void guestOrderTest() {
        // given
        GuestOrder testGuestOrder = GuestOrder.builder()
                .id("testId")
                .order(testOrder)
                .password("testPassword")
                .build();
        em.persist(testGuestOrder);

        // when
        Optional<GuestOrder> optGuestOrder = guestOrderRepository.findById(testGuestOrder.getId());

        // then
        assertTrue(optGuestOrder.isPresent());
        GuestOrder guestOrder = optGuestOrder.get();
        assertEquals(testGuestOrder, guestOrder);
        assertEquals(testGuestOrder.getId(), guestOrder.getId());
        assertEquals(testGuestOrder.getOrder(), guestOrder.getOrder());
        assertEquals(testGuestOrder.getPassword(), guestOrder.getPassword());
    }
}
