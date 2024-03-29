package com.t3t.bookstoreapi;


import com.t3t.bookstoreapi.payment.entity.Orders;
import com.t3t.bookstoreapi.payment.entity.PaymentProvider;
import com.t3t.bookstoreapi.payment.entity.Payments;
import com.t3t.bookstoreapi.payment.entity.TossPayments;
import com.t3t.bookstoreapi.payment.repository.PaymentProviderRepository;

import com.t3t.bookstoreapi.payment.repository.PaymentRepository;
import com.t3t.bookstoreapi.payment.repository.TossPaymentRepository;
import com.t3t.bookstoreapi.payment.repository.orderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class EntityMappingTest {

    @Autowired
    private orderRepository ordersRepository;

    @Autowired
    private PaymentProviderRepository paymentProviderRepository;

    @Autowired
    private PaymentRepository paymentsRepository;

    @Autowired
    private TossPaymentRepository tossPaymentsRepository;

    @Test
    @DisplayName("Entity Mapping test")
    public void testEntitiesMapping() {
        // Create a payment provider
        PaymentProvider paymentProvider = new PaymentProvider();
        paymentProvider.setPaymentProviderId(1);
        paymentProvider.setPaymentProviderName("Test Payment Provider");
        paymentProviderRepository.save(paymentProvider);

        // Create an order
        Orders order = new Orders();
        order.setOrderId(1L);
        ordersRepository.save(order);

        // Create a payment
        Payments payment = new Payments();
        payment.setOrderId(order);
        payment.setPaymentProviderId(paymentProvider);
        LocalDateTime specificTime = LocalDateTime.of(2024, 3, 29, 14, 50, 52);
        payment.setPaymentTime(specificTime);
        payment.setPaymentPrice(BigDecimal.valueOf(100));
        paymentsRepository.save(payment);


        // Verify payment persistence
        Payments savedPayment = paymentsRepository.findById(payment.getPaymentId()).orElse(null);
        assertNotNull(savedPayment);
        assertEquals(order.getOrderId(), savedPayment.getOrderId().getOrderId());
        assertEquals(paymentProvider.getPaymentProviderId(), savedPayment.getPaymentProviderId().getPaymentProviderId());
        assertEquals(payment.getPaymentTime(), savedPayment.getPaymentTime());
        assertEquals(payment.getPaymentPrice(), BigDecimal.valueOf(100));

        // Create a Toss payment
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

import com.t3t.bookstoreapi.member.repository.MemberRepository;
import com.t3t.bookstoreapi.order.model.entity.Delivery;
import com.t3t.bookstoreapi.order.model.entity.OrderStatus;
import com.t3t.bookstoreapi.order.model.entity.Packaging;
import com.t3t.bookstoreapi.order.repository.DeliveryRepository;
import com.t3t.bookstoreapi.order.repository.OrderStatusRepository;
import com.t3t.bookstoreapi.order.repository.OrderRepository;
import com.t3t.bookstoreapi.order.repository.PackagingRepository;
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
import java.util.Optional;

@SpringBootTest
@Transactional
@Slf4j
@ActiveProfiles("prod")
class EntityMappingTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OrderStatusRepository orderStatusRepository;

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private OrderRepository OrderRepository;

    @Autowired
    private PackagingRepository packagingRepository;

    /**
     * OrderStatus 엔티티 맵핑 테스트
     * @author woody33545(구건모)
     */
    @Test
    @DisplayName("OrderStatus 엔티티 맵핑 테스트")
    void orderStatusTest() {
        // given
        String orderStatusName = "testOrderStatus";
        OrderStatus orderStatus = orderStatusRepository.save(OrderStatus.builder().name(orderStatusName).build());

        // when
        Optional<OrderStatus> resultOrderStatus = orderStatusRepository.findById(orderStatus.getId());

        // then
        Assertions.assertTrue(resultOrderStatus.isPresent());
        Assertions.assertEquals(orderStatus, resultOrderStatus.get());
    }

    /**
     * Delivery 엔티티 맵핑 테스트
     * @author woody33545(구건모)
     */
    @Test
    @DisplayName("Delivery 엔티티 맵핑 테스트")
    void deliveryTest() {
        // given
        BigDecimal price = BigDecimal.valueOf(10000);
        int addressNumber = 12345;
        String roadnameAddress = "대전광역시 유성구";
        String detailAddress = "궁동";
        String recipientName = "woody";
        String recipientPhoneNumber = "010-1234-5678";
        LocalDate deliveryDate = LocalDate.now();

        Delivery delivery = deliveryRepository.save(Delivery.builder()
                .price(price)
                .addressNumber(addressNumber)
                .roadnameAddress(roadnameAddress)
                .detailAddress(detailAddress)
                .recipientName(recipientName)
                .recipientPhoneNumber(recipientPhoneNumber)
                .deliveryDate(deliveryDate)
                .build());

        // when
        Optional<Delivery> resultDelivery = deliveryRepository.findById(delivery.getId());

        // then
        Assertions.assertTrue(resultDelivery.isPresent());
        Assertions.assertEquals(delivery, resultDelivery.get());
    }

    @Test
    @DisplayName("Package 엔티티 맵핑 테스트")
    void PackageTest(){

        // given
        String packageName = "testPackageName";
        BigDecimal packagePrice = BigDecimal.valueOf(10000);

        Packaging packaging = Packaging.builder()
                .name(packageName)
                .price(packagePrice)
                .build();

        Packaging resultPack = packagingRepository.save(packaging);

        // when
        Optional<Packaging> resultPackaging = packagingRepository.findById(resultPack.getId());

        //then
        Assertions.assertTrue(resultPackaging.isPresent());
        Assertions.assertEquals(packaging, resultPackaging.get());
    }
}
