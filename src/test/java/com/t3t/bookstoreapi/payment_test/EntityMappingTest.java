package com.t3t.bookstoreapi.payment_test;

import com.t3t.bookstoreapi.member.model.constant.MemberRole;
import com.t3t.bookstoreapi.member.model.constant.MemberStatus;
import com.t3t.bookstoreapi.member.model.entity.Member;
import com.t3t.bookstoreapi.member.model.entity.MemberGrade;
import com.t3t.bookstoreapi.member.model.entity.MemberGradePolicy;
import com.t3t.bookstoreapi.member.repository.MemberGradePolicyRepository;
import com.t3t.bookstoreapi.member.repository.MemberGradeRepository;
import com.t3t.bookstoreapi.order.model.entity.Delivery;
import com.t3t.bookstoreapi.order.model.entity.Order;
import com.t3t.bookstoreapi.order.repository.OrderRepository;
import com.t3t.bookstoreapi.payment.model.entity.PaymentProvider;
import com.t3t.bookstoreapi.payment.model.entity.Payments;
import com.t3t.bookstoreapi.payment.model.entity.TossPayments;
import com.t3t.bookstoreapi.payment.repository.PaymentProviderRepository;
import com.t3t.bookstoreapi.payment.repository.PaymentRepository;
import com.t3t.bookstoreapi.payment.repository.TossPaymentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class EntityMappingTest {

    @Autowired
    private OrderRepository ordersRepository;

    @Autowired
    private PaymentProviderRepository paymentProviderRepository;

    @Autowired
    private PaymentRepository paymentsRepository;

    @Autowired
    private TossPaymentRepository tossPaymentsRepository;

    @Autowired
    private MemberGradePolicyRepository memberGradePolicyRepository;

    @Autowired
    private MemberGradeRepository memberGradeRepository;

    @Test
    @DisplayName("Entity Mapping test")
    public void testEntitiesMapping() {
        PaymentProvider paymentProvider = new PaymentProvider();
        paymentProvider.setPaymentProviderId(1);
        paymentProvider.setPaymentProviderName("Test Payment Provider");
        paymentProviderRepository.save(paymentProvider);

        MemberGradePolicy memberGradePolicy = memberGradePolicyRepository.save(MemberGradePolicy.builder()
                .startAmount(BigDecimal.valueOf(0))
                .endAmount(BigDecimal.valueOf(100000))
                .build());

        MemberGrade memberGrade = memberGradeRepository.save(MemberGrade.builder()
                .policy(memberGradePolicy)
                .name("test")
                .build());

        Order order = new Order();
        order.setId(1L);
        order.setMember(Member.builder()
                .id(1L)
                .gradeId(memberGrade)
                .name("test")
                .phone("010-1234-1234")
                .email("g@naver.com")
                .birthDate(LocalDate.now())
                .latestLogin(LocalDateTime.now())
                .point(1L)
                .status(MemberStatus.ACTIVE)
                .role(MemberRole.USER)
                .build());
        order.setDelivery(Delivery.builder()
                .id(0L)
                .deliveryDate(LocalDate.now())
                .price(BigDecimal.valueOf(10000))
                .addressNumber(12345)
                .roadnameAddress("testRoadnameAddress0")
                .detailAddress("testDetailAddress0")
                .recipientName("testRecipientName0")
                .recipientPhoneNumber("testRecipientPhoneNumber0")
                .build());
        order.setOrderDatetime(LocalDateTime.now());
        ordersRepository.save(order);

        Payments payment = new Payments();
        payment.setOrderId(order);
        payment.setPaymentProviderId(paymentProvider);
        LocalDateTime specificTime = LocalDateTime.of(2024, 3, 29, 14, 50, 52);
        payment.setPaymentTime(specificTime);
        payment.setPaymentPrice(BigDecimal.valueOf(100));
        paymentsRepository.save(payment);

        Payments savedPayment = paymentsRepository.findById(payment.getPaymentId()).orElse(null);
        assertNotNull(savedPayment);
        assertEquals(order.getId(), savedPayment.getOrderId().getId());
        assertEquals(paymentProvider.getPaymentProviderId(), savedPayment.getPaymentProviderId().getPaymentProviderId());
        assertEquals(payment.getPaymentTime(), savedPayment.getPaymentTime());
        assertEquals(payment.getPaymentPrice(), BigDecimal.valueOf(100));

        TossPayments.TossPaymentId tossPaymentId = new TossPayments.TossPaymentId();
        tossPaymentId.setPayment(payment);
        TossPayments tossPayment = TossPayments.builder()
                .tossPaymentId(tossPaymentId)
                .tossOrderId("toss_order_id")
                .tossPaymentKey("toss_payment_key")
                .tossPaymentStatus("SUCCESS")
                .tossPaymentReceiptUrl("http://example.com")
                .build();
        tossPaymentsRepository.save(tossPayment);

        TossPayments savedTossPayment = tossPaymentsRepository.findById(tossPayment.getTossPaymentId()).orElse(null);
        assertNotNull(savedTossPayment);
        assertEquals("toss_order_id", savedTossPayment.getTossOrderId());
        assertEquals("toss_payment_key", savedTossPayment.getTossPaymentKey());
        assertEquals("SUCCESS", savedTossPayment.getTossPaymentStatus());
        assertEquals("http://example.com", savedTossPayment.getTossPaymentReceiptUrl());
    }
}
