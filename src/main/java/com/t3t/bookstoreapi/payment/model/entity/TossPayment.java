package com.t3t.bookstoreapi.payment.model.entity;

import com.t3t.bookstoreapi.payment.constant.TossPaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Payment 엔티티에 대한 서브 타입으로 토스 결제 정보를 담는 엔티티<br>
 * 토스에서 제공하는 Toss Payment 객체 중 북스토어 서비스에서 필요한 정보만을 정의한다.
 * @see <a href="https://docs.tosspayments.com/reference#payment-%EA%B0%9D%EC%B2%B4">toss api `payment` object reference </a>
 * @author woody35545(구건모)
 */
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "toss_payments")
public class TossPayment extends Payment{

    /**
     * 결제 데이터 관리를 위해 반드시 저장해야하는 값으로 결제 상태가 변해도 값이 유지된다.<br>
     * 결제 승인, 결제 조회, 결제 취소 API에 사용된다.
     * @author woody35545(구건모)
     */
    @Column(name = "toss_payment_key", length = 200, nullable = false)
    @Comment("토스에서 발급되는 결제키")
    private String tossPaymentKey;

    /**
     * 토스에서 사용되는 주문 식별자로 최소 6자에서 최대 64자로 제한된다.<br>
     * 토스 주문 식별자는 클라이언트인 북스토어 서비스에서 결제 요청시 직접 생성하여 토스 API 에 전달한다.
     * @apiNote 북스토어 서비스에서 사용하는 자체적인 주문 식별자와 1대1 로 대응되는 값이지만 동일한 형태는 아님에 주의한다.
     * @author woody35545(구건모)
     */
    @Column(name = "toss_order_id", length = 64, nullable = false)
    @Comment("토스에서 사용되는 주문 식별자")
    private String tossOrderId;

    @Enumerated(EnumType.STRING)
    @Column(name = "toss_payment_status", length = 50, nullable = false)
    @Comment("토스 결제 상태")
    private TossPaymentStatus tossPaymentStatus;

    @Column(name = "toss_payment_receipt_url", nullable = false, columnDefinition = "TEXT")
    @Comment("토스 결제 영수증 URL")
    private String tossPaymentReceiptUrl;

    @Column(name = "toss_payment_requested_at", nullable = false)
    @Comment("토스 결제가 요청된 시간")
    private LocalDateTime tossPaymentRequestedAt;

    @Column(name = "toss_payment_approved_at", nullable = false)
    @Comment("토스 결제가 승인된 시간")
    private LocalDateTime tossPaymentApprovedAt;

}
