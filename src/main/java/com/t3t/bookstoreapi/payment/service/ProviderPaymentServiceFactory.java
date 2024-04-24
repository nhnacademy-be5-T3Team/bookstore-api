package com.t3t.bookstoreapi.payment.service;

import com.t3t.bookstoreapi.payment.constant.PaymentProviderType;
import com.t3t.bookstoreapi.payment.exception.UnsupportedPaymentProviderTypeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 결제 제공자에 따라 적절한 결제 서비스를 반환하는 팩토리 클래스
 *
 * @author woody35545(구건모)
 */

@Service
@RequiredArgsConstructor
public class ProviderPaymentServiceFactory {

    public final Map<String, ProviderPaymentService> providerPaymentServiceMap;
    private static final String DEFAULT_PROVIDER_SERVICE_POSTFIX = "paymentService";

    public ProviderPaymentService get(PaymentProviderType providerType) {
        String providerServiceBeanName = providerType.name().toLowerCase() + DEFAULT_PROVIDER_SERVICE_POSTFIX;

        if (!providerPaymentServiceMap.containsKey(providerServiceBeanName)) {
            throw new UnsupportedPaymentProviderTypeException(providerType.toString());
        }

        return providerPaymentServiceMap.get(providerServiceBeanName);
    }
}
