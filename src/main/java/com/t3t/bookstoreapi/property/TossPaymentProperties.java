package com.t3t.bookstoreapi.property;

import lombok.Builder;
import lombok.Getter;

/**
 * Toss 결제 서비스에서 사용되는 프로퍼티
 * @author woody35545(구건모)
 */
@Builder
@Getter
public class TossPaymentProperties {
    private String widgetSecretKey;
}
