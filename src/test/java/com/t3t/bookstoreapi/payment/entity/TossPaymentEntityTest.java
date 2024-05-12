package com.t3t.bookstoreapi.payment.entity;

import com.t3t.bookstoreapi.member.model.constant.MemberRole;
import com.t3t.bookstoreapi.member.model.constant.MemberStatus;
import com.t3t.bookstoreapi.member.model.entity.Member;
import com.t3t.bookstoreapi.member.model.entity.MemberGrade;
import com.t3t.bookstoreapi.member.model.entity.MemberGradePolicy;
import com.t3t.bookstoreapi.member.repository.MemberGradePolicyRepository;
import com.t3t.bookstoreapi.member.repository.MemberGradeRepository;
import com.t3t.bookstoreapi.member.repository.MemberRepository;
import com.t3t.bookstoreapi.order.model.entity.Delivery;
import com.t3t.bookstoreapi.order.model.entity.Order;
import com.t3t.bookstoreapi.order.repository.DeliveryRepository;
import com.t3t.bookstoreapi.order.repository.OrderRepository;
import com.t3t.bookstoreapi.payment.constant.PaymentProviderType;
import com.t3t.bookstoreapi.payment.constant.TossPaymentStatus;
import com.t3t.bookstoreapi.payment.model.entity.Payment;
import com.t3t.bookstoreapi.payment.model.entity.PaymentProvider;
import com.t3t.bookstoreapi.payment.model.entity.TossPayment;
import com.t3t.bookstoreapi.payment.repository.PaymentProviderRepository;
import com.t3t.bookstoreapi.payment.repository.PaymentRepository;
import com.t3t.bookstoreapi.payment.repository.TossPaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * TossPaymentEntityTest 엔티티 테스트<br>
 * 테스트용 데이터베이스에 접근하기 위해 Secure Key Manager 를 사용하므로 테스트를 실행할 때 이와 관련된 환경변수 설정이 필요하다.<br>
 * Secure Key Manager 와 DataSource 관련 빈들을 사용하기 위해 @DataJpaTest 가 아닌 @SpringBootTest 를 사용하여 통합테스트로 진행한다.
 *
 * @author woody35545(구건모)
 */
@Slf4j
@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class TossPaymentEntityTest {
    @Autowired
    private PaymentProviderRepository paymentProviderRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberGradeRepository memberGradeRepository;
    @Autowired
    private MemberGradePolicyRepository memberGradePolicyRepository;
    @Autowired
    private DeliveryRepository deliveryRepository;
    @Autowired
    private TossPaymentRepository tossPaymentRepository;

    /**
     * TossPayment 엔티티 테스트
     *
     * @see TossPayment
     * @author woody35545(구건모)
     */
    @Test
    @DisplayName("TossPayment 엔티티 테스트")
    void tossPaymentEntityTest() {
        // given
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
                .email("test@mail.com")
                .point(1000L)
                .phone("010-1234-5678")
                .latestLogin(LocalDateTime.now())
                .birthDate(LocalDateTime.now().toLocalDate())
                .grade(memberGrade)
                .status(MemberStatus.ACTIVE)
                .role(MemberRole.USER)
                .build());

        PaymentProvider paymentProvider = paymentProviderRepository.save(PaymentProvider.builder()
                .id(1L)
                .name(PaymentProviderType.TOSS)
                .build());

        log.info("paymentProvider => {}", paymentProvider);

        Delivery delivery = deliveryRepository.save(Delivery.builder()
                .price(BigDecimal.valueOf(10000))
                .addressNumber(12345)
                .roadnameAddress("testRoadnameAddress")
                .detailAddress("testDetailAddress")
                .recipientName("testRecipientName")
                .recipientPhoneNumber("010-1234-5678")
                .deliveryDate(LocalDate.now())
                .build());

        Order order = orderRepository.save(Order.builder()
                .member(member)
                .delivery(delivery)
                .createdAt(LocalDateTime.now())
                .build());

        TossPayment tossPayment = tossPaymentRepository.save(TossPayment.builder()
                .paymentProvider(paymentProvider)
                .order(order)
                .totalAmount(BigDecimal.valueOf(10000))
                .createdAt(LocalDateTime.now())
                .tossPaymentKey("test_key")
                .tossOrderId("test_order_id")
                .tossPaymentStatus(TossPaymentStatus.READY)
                .tossPaymentReceiptUrl("https://test-url.com")
                .tossPaymentRequestedAt(LocalDateTime.now())
                .tossPaymentApprovedAt(LocalDateTime.now())
                .build());

        // when
        Optional<TossPayment> optTossPayment = tossPaymentRepository.findById(tossPayment.getId());
        Optional<Payment> optPayment = paymentRepository.findById(tossPayment.getId());

        // then
        Assertions.assertTrue(optTossPayment.isPresent());
        Assertions.assertEquals(tossPayment, optTossPayment.get());
        Assertions.assertTrue(optPayment.isPresent());
        Assertions.assertEquals(tossPayment, optPayment.get());
    }
}
