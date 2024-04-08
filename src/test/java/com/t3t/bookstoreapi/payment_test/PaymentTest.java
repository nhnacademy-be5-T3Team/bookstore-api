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
import com.t3t.bookstoreapi.payment.repository.PaymentProviderRepository;
import com.t3t.bookstoreapi.payment.repository.PaymentRepository;
import com.t3t.bookstoreapi.payment.model.request.PaymentRequest;
import com.t3t.bookstoreapi.payment.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class PaymentTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private OrderRepository ordersRepository;

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private PaymentProviderRepository paymentProviderRepository;

    @InjectMocks
    private PaymentService paymentService;
    @Mock
    private Payments payments;

    @Mock
    private MemberGradePolicyRepository memberGradePolicyRepository;

    @Mock
    private MemberGradeRepository memberGradeRepository;

    @BeforeEach
    public void setUp() {
    }

    @Test
    @DisplayName("payment 객체 생성 test")
    public void testCreatePayment() {
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


        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setOrderId(order.getId());
        paymentRequest.setPaymentPrice(BigDecimal.valueOf(10000));

        when(ordersRepository.findById(order.getId())).thenReturn(Optional.of(order));

        PaymentProvider paymentProvider = new PaymentProvider();
        paymentProvider.setPaymentProviderName("토스");
        when(paymentProviderRepository.findByPaymentProviderName("토스")).thenReturn(paymentProvider);

        when(paymentRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        paymentService.PaymentRequest(paymentRequest);

        // 주문 정보를 가져오는 메서드가 정확히 호출되었는지 확인
        verify(ordersRepository, times(1)).findById(order.getId());

        // 결제 정보를 저장하는 메서드가 정확히 호출되었는지 확인
        verify(paymentRepository, times(1)).save(any());

    }
}

