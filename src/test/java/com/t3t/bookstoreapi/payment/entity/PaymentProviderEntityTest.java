package com.t3t.bookstoreapi.payment.entity;

import com.t3t.bookstoreapi.payment.constant.PaymentProviderType;
import com.t3t.bookstoreapi.payment.model.entity.PaymentProvider;
import com.t3t.bookstoreapi.payment.repository.PaymentProviderRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

/**
 * PaymentProviderEntityTest 엔티티 테스트<br>
 * 테스트용 데이터베이스에 접근하기 위해 Secure Key Manager 를 사용하므로 테스트를 실행할 때 이와 관련된 환경변수 설정이 필요하다.<br>
 * Secure Key Manager 와 DataSource 관련 빈들을 사용하기 위해 @DataJpaTest 가 아닌 @SpringBootTest 를 사용하여 통합테스트로 진행한다.
 *
 * @author woody35545(구건모)
 */
@Slf4j
@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class PaymentProviderEntityTest {

    @Autowired
    private PaymentProviderRepository paymentProviderRepository;

    /**
     * PaymentProvider 엔티티 테스트
     *
     * @see PaymentProvider
     * @author woody35545(구건모)
     */
    @Test
    @DisplayName("PaymentProvider 엔티티 테스트")
    void paymentProviderEntityTest() {
        // given
        PaymentProvider paymentProvider =
                paymentProviderRepository.save(PaymentProvider.builder()
                        .id(1L)
                        .name(PaymentProviderType.TOSS)
                        .build());

        // when
        Optional<PaymentProvider> optPaymentProvider = paymentProviderRepository.findById(paymentProvider.getId());

        // then
        Assertions.assertTrue(optPaymentProvider.isPresent());
        Assertions.assertEquals(paymentProvider, optPaymentProvider.get());
    }
}
