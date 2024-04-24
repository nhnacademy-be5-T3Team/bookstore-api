package com.t3t.bookstoreapi.payment.service;

import com.t3t.bookstoreapi.book.repository.BookRepository;
import com.t3t.bookstoreapi.order.exception.OrderNotFoundForIdException;
import com.t3t.bookstoreapi.order.model.entity.Order;
import com.t3t.bookstoreapi.order.repository.OrderRepository;
import com.t3t.bookstoreapi.payment.client.TossPaymentApiClient;
import com.t3t.bookstoreapi.payment.constant.PaymentProviderType;
import com.t3t.bookstoreapi.payment.constant.TossPaymentStatus;
import com.t3t.bookstoreapi.payment.exception.PaymentProviderNotFoundForNameException;
import com.t3t.bookstoreapi.payment.exception.UnsupportedPaymentProviderTypeException;
import com.t3t.bookstoreapi.payment.model.dto.PaymentDto;
import com.t3t.bookstoreapi.payment.model.entity.Payment;
import com.t3t.bookstoreapi.payment.model.entity.PaymentProvider;
import com.t3t.bookstoreapi.payment.model.entity.TossPayment;
import com.t3t.bookstoreapi.payment.model.request.PaymentConfirmRequest;
import com.t3t.bookstoreapi.payment.model.request.PaymentCreationRequest;
import com.t3t.bookstoreapi.payment.model.response.PaymentConfirmResponse;
import com.t3t.bookstoreapi.payment.repository.PaymentProviderRepository;
import com.t3t.bookstoreapi.payment.repository.TossPaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TossPaymentService implements ProviderPaymentService {

    private final PaymentProviderRepository paymentProviderRepository;
    private final TossPaymentApiClient tossPaymentApiAdaptor;
    private final TossPaymentRepository tossPaymentRepository;
    private final OrderRepository orderRepository;

    @Override
    public PaymentConfirmResponse confirmPayment(PaymentConfirmRequest paymentRequestBody) {
        return tossPaymentApiAdaptor.confirmPayment(paymentRequestBody);
    }

    @Override
    public PaymentDto createPayment(PaymentCreationRequest request) {

        PaymentProvider paymentProvider = paymentProviderRepository.findByName(PaymentProviderType.TOSS.toString())
                .orElseThrow(() -> new PaymentProviderNotFoundForNameException(PaymentProviderType.TOSS.toString()));

        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new OrderNotFoundForIdException(request.getOrderId()));

        TossPayment tossPayment = tossPaymentRepository.save(TossPayment.builder()
                .order(order)
                .paymentProvider(paymentProvider)
                .totalAmount(request.getTotalAmount())
                .createdAt(LocalDateTime.now())
                .tossPaymentKey(request.getProviderPaymentKey())
                .tossOrderId(request.getProviderOrderId())
                .tossPaymentStatus(TossPaymentStatus.valueOf(request.getProviderPaymentStatus()))
                .tossPaymentReceiptUrl(request.getProviderPaymentReceiptUrl())
                .tossPaymentRequestedAt(request.getProviderPaymentRequestedAt())
                .tossPaymentApprovedAt(request.getProviderPaymentApprovedAt())
                .build());

        return PaymentDto.builder()
                .paymentId(tossPayment.getId())
                .orderId(order.getId())
                .paymentProvider(paymentProvider)
                .totalAmount(tossPayment.getTotalAmount())
                .createdAt(tossPayment.getCreatedAt())
                .providerPaymentKey(tossPayment.getTossPaymentKey())
                .providerOrderId(tossPayment.getTossOrderId())
                .providerPaymentStatus(tossPayment.getTossPaymentStatus().name())
                .providerPaymentReceiptUrl(tossPayment.getTossPaymentReceiptUrl())
                .providerPaymentRequestedAt(tossPayment.getTossPaymentRequestedAt())
                .providerPaymentApprovedAt(tossPayment.getTossPaymentApprovedAt())
                .build();
    }
}
