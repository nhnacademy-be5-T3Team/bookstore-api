package com.t3t.bookstoreapi.order.service;

import com.t3t.bookstoreapi.book.exception.BookNotFoundForIdException;
import com.t3t.bookstoreapi.book.model.entity.Book;
import com.t3t.bookstoreapi.book.repository.BookRepository;
import com.t3t.bookstoreapi.member.exception.MemberAddressNotFoundForIdException;
import com.t3t.bookstoreapi.member.model.dto.MemberAddressDto;
import com.t3t.bookstoreapi.member.repository.MemberAddressRepository;
import com.t3t.bookstoreapi.order.constant.OrderStatusType;
import com.t3t.bookstoreapi.order.exception.OrderStatusNotFoundForNameException;
import com.t3t.bookstoreapi.order.exception.PackagingNotFoundForIdException;
import com.t3t.bookstoreapi.order.exception.PaymentAmountMismatchException;
import com.t3t.bookstoreapi.order.model.dto.DeliveryDto;
import com.t3t.bookstoreapi.order.model.dto.GuestOrderDto;
import com.t3t.bookstoreapi.order.model.dto.OrderDto;
import com.t3t.bookstoreapi.order.model.request.*;
import com.t3t.bookstoreapi.order.model.response.GuestOrderPreparationResponse;
import com.t3t.bookstoreapi.order.model.response.MemberOrderPreparationResponse;
import com.t3t.bookstoreapi.order.model.response.OrderDetailInfoResponse;
import com.t3t.bookstoreapi.order.repository.OrderStatusRepository;
import com.t3t.bookstoreapi.order.repository.PackagingRepository;
import com.t3t.bookstoreapi.payment.model.request.PaymentConfirmRequest;
import com.t3t.bookstoreapi.payment.model.request.PaymentCreationRequest;
import com.t3t.bookstoreapi.payment.model.response.PaymentConfirmResponse;
import com.t3t.bookstoreapi.payment.service.ProviderPaymentServiceFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class OrderServiceFacade {
    // 기본 배송비는 임의로 설정. 상의 후 변경 예정
    private static final BigDecimal DEFAULT_DELIVERY_PRICE = new BigDecimal("3000");

    private final BookRepository bookRepository;
    private final PackagingRepository packagingRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final OrderService orderService;
    private final OrderDetailService orderDetailService;
    private final DeliveryService deliveryService;
    private final GuestOrderService guestOrderService;
    private final ProviderPaymentServiceFactory providerPaymentServiceFactory;
    private final MemberAddressRepository memberAddressRepository;

    /**
     * 회원 임시 주문 생성<br>
     * 결제가 진행되지 않은 상태의 임시 주문을 생성한다.<br>
     * 결제 완료 후, 주문 확정 요청을 통해 주문 상태가 변경된다.
     *
     * @author woody35545(구건모)
     * @see com.t3t.bookstoreapi.order.exception.BookStockShortageException
     */
    public MemberOrderPreparationResponse prepareOrder(MemberOrderPreparationRequest memberOrderPreparationRequest) {

        /**
         * 재고 감소
         * 결제 페이지에 먼저 진입한 고객이 재고를 선점할 수 있도록 재고를 미리 감소시킨다.
         * 임시 주문이 생성된 후 15 분 이내에 결제가 완료되지 않거나, 결제에 실패한 경우 재고를 복구시킨다.
         * 만약 재고가 부족한 경우 예외가 발생하며 임시 주문을 생성하지 않는다.
         */
        memberOrderPreparationRequest.getOrderDetailInfoList().stream()
                .forEach(orderDetailInfo -> {
                    bookRepository.getBookByIdUsingLock(orderDetailInfo.getBookId())
                            .orElseThrow(() -> new BookNotFoundForIdException(orderDetailInfo.getBookId()))
                            .decreaseStock(orderDetailInfo.getQuantity());
                });

        /**
         * 배송 정보 생성
         */
        Long memberAddressId = memberOrderPreparationRequest.getMemberAddressId();
        Integer addressNumber = memberOrderPreparationRequest.getAddressNumber();
        String roadnameAddress = memberOrderPreparationRequest.getRoadnameAddress();
        String detailAddress = memberOrderPreparationRequest.getDetailAddress();

        // 회원 주소 목록에서 기존 주소를 선택한 경우
        if (memberAddressId != null) {
            MemberAddressDto memberAddress = memberAddressRepository.getMemberAddressDtoById(memberAddressId)
                    .orElseThrow(() -> new MemberAddressNotFoundForIdException(memberAddressId));

            addressNumber = memberAddress.getAddressNumber();
            roadnameAddress = memberAddress.getRoadNameAddress();
            detailAddress = memberAddress.getAddressDetail();
        }

        DeliveryDto deliveryDto = deliveryService.createDelivery(DeliveryCreationRequest.builder()
                .price(DEFAULT_DELIVERY_PRICE)
                .detailAddress(detailAddress)
                .addressNumber(addressNumber)
                .roadnameAddress(roadnameAddress)
                .deliveryDate(memberOrderPreparationRequest.getDeliveryDate())
                .recipientName(memberOrderPreparationRequest.getRecipientName())
                .recipientPhoneNumber(memberOrderPreparationRequest.getRecipientPhoneNumber())
                .build());

        /**
         * 주문 정보 생성
         */
        OrderDto orderDto = orderService.createOrder(MemberOrderCreationRequest.builder()
                .memberId(memberOrderPreparationRequest.getMemberId())
                .deliveryId(deliveryDto.getId())
                .build());

        /**
         * 주문 상세 생성
         */
        List<MemberOrderPreparationRequest.OrderDetailInfo> orderDetailInfoList = memberOrderPreparationRequest.getOrderDetailInfoList();

        BigDecimal totalPrice = orderDetailInfoList.stream()
                .map(orderDetailInfo -> {
                    Book book = bookRepository.findById(orderDetailInfo.getBookId())
                            .orElseThrow(() -> new BookNotFoundForIdException(orderDetailInfo.getBookId()));

                    BigDecimal bookPrice = book.getBookPrice();
                    BigDecimal bookDiscount = book.getBookDiscount();

                    BigDecimal currentPrice = bookPrice.subtract(bookPrice.multiply(bookDiscount.divide(BigDecimal.valueOf(100))));

                    if (orderDetailInfo.getPackagingId() != null) {
                        currentPrice = currentPrice.add(packagingRepository.findById(orderDetailInfo.getPackagingId())
                                .orElseThrow(() -> new PackagingNotFoundForIdException(orderDetailInfo.getPackagingId())).getPrice());
                    }

                    // 주문 상세 생성
                    orderDetailService.createOrderDetail(OrderDetailCreationRequest.builder()
                            .bookId(orderDetailInfo.getBookId())
                            .orderId(orderDto.getId())
                            .orderStatus(orderStatusRepository.findByName(OrderStatusType.PENDING.name())
                                    .orElseThrow(() -> new OrderStatusNotFoundForNameException(OrderStatusType.PENDING.toString())))
                            .quantity(orderDetailInfo.getQuantity())
                            .packagingId(orderDetailInfo.getPackagingId())
                            .price(currentPrice.multiply(BigDecimal.valueOf(orderDetailInfo.getQuantity())))
                            .build());

                    return currentPrice.multiply(BigDecimal.valueOf(orderDetailInfo.getQuantity()));

                }).reduce(BigDecimal.ZERO, BigDecimal::add);

        return MemberOrderPreparationResponse.builder()
                .memberId(memberOrderPreparationRequest.getMemberId())
                .providerOrderId(UUID.randomUUID().toString().replace("-", ""))
                .totalPrice(totalPrice)
                .orderId(orderDto.getId())
                .deliveryId(deliveryDto.getId())
                .build();
    }


    /**
     * 비회원 임시 주문 생성<br>
     * 결제가 진행되지 않은 상태의 임시 주문을 생성한다.<br>
     * 결제 완료 후, 주문 확정 요청을 통해 주문 상태가 변경된다.
     *
     * @param orderPrepareRequest 비회원 주문 생성 요청 객체
     * @author woody35545(구건모)
     */
    public GuestOrderPreparationResponse prepareOrder(GuestOrderPreparationRequest orderPrepareRequest) {
        /**
         * 재고 감소
         * 결제 페이지에 먼저 진입한 고객이 재고를 선점할 수 있도록 재고를 미리 감소시킨다.
         * 임시 주문이 생성된 후 15 분 이내에 결제가 완료되지 않거나, 결제에 실패한 경우 재고를 복구시킨다.
         * 만약 재고가 부족한 경우 예외가 발생하며 임시 주문을 생성하지 않는다.
         */
        orderPrepareRequest.getOrderDetailInfoList().stream()
                .forEach(orderDetailInfo -> {
                    bookRepository.getBookByIdUsingLock(orderDetailInfo.getBookId())
                            .orElseThrow(() -> new BookNotFoundForIdException(orderDetailInfo.getBookId()))
                            .decreaseStock(orderDetailInfo.getQuantity());
                });

        /**
         * 배송 정보 생성
         */
        DeliveryDto deliveryDto = deliveryService.createDelivery(DeliveryCreationRequest.builder()
                .price(DEFAULT_DELIVERY_PRICE)
                .detailAddress(orderPrepareRequest.getDetailAddress())
                .addressNumber(orderPrepareRequest.getAddressNumber())
                .roadnameAddress(orderPrepareRequest.getRoadnameAddress())
                .deliveryDate(orderPrepareRequest.getDeliveryDate())
                .recipientName(orderPrepareRequest.getRecipientName())
                .recipientPhoneNumber(orderPrepareRequest.getRecipientPhoneNumber())
                .build());

        /**
         * 주문 정보 생성
         */
        OrderDto orderDto = orderService.createOrder(MemberOrderCreationRequest.builder()
                .memberId(null)
                .deliveryId(deliveryDto.getId())
                .build());

        GuestOrderDto guestOrderDto = guestOrderService.createGuestOrder(GuestOrderCreationRequest.builder()
                .orderId(orderDto.getId())
                .password(orderPrepareRequest.getGuestOrderPassword())
                .build());

        /**
         * 주문 상세 생성
         */
        List<GuestOrderPreparationRequest.OrderDetailInfo> orderDetailInfoList = orderPrepareRequest.getOrderDetailInfoList();

        BigDecimal totalPrice = orderDetailInfoList.stream()
                .map(orderDetailInfo -> {
                    Book book = bookRepository.findById(orderDetailInfo.getBookId())
                            .orElseThrow(() -> new BookNotFoundForIdException(orderDetailInfo.getBookId()));

                    BigDecimal bookPrice = book.getBookPrice();
                    BigDecimal bookDiscount = book.getBookDiscount();

                    BigDecimal currentPrice = bookPrice.subtract(bookPrice.multiply(bookDiscount.divide(BigDecimal.valueOf(100))));

                    if (orderDetailInfo.getPackagingId() != null) {
                        currentPrice = currentPrice.add(packagingRepository.findById(orderDetailInfo.getPackagingId())
                                .orElseThrow(() -> new PackagingNotFoundForIdException(orderDetailInfo.getPackagingId())).getPrice());
                    }

                    // 주문 상세 생성
                    orderDetailService.createOrderDetail(OrderDetailCreationRequest.builder()
                            .bookId(orderDetailInfo.getBookId())
                            .orderId(orderDto.getId())
                            .orderStatus(orderStatusRepository.findByName(OrderStatusType.PENDING.name())
                                    .orElseThrow(() -> new OrderStatusNotFoundForNameException(OrderStatusType.PENDING.toString())))
                            .quantity(orderDetailInfo.getQuantity())
                            .packagingId(orderDetailInfo.getPackagingId())
                            .price(currentPrice.multiply(BigDecimal.valueOf(orderDetailInfo.getQuantity())))
                            .build());

                    return currentPrice.multiply(BigDecimal.valueOf(orderDetailInfo.getQuantity()));

                }).reduce(BigDecimal.ZERO, BigDecimal::add);


        return GuestOrderPreparationResponse.builder()
                .providerOrderId(UUID.randomUUID().toString().replace("-", ""))
                .guestOrderId(guestOrderDto.getId())
                .totalPrice(totalPrice)
                .orderId(orderDto.getId())
                .deliveryId(deliveryDto.getId())
                .build();
    }

    /**
     * 주문 승인<br>
     * 결제가 완료된 결제를 검증하고 승인한다.<br>
     * 성공적으로 승인되면, 주문 상태가 승인(CONFIRMED)으로 변경된다.<br>
     *
     * @param request 주문 승인 요청 객체
     * @author woody35545(구건모)
     */
    public void confirmOrder(OrderConfirmRequest request) {

        PaymentConfirmResponse paymentConfirmResponse = providerPaymentServiceFactory.get(request.getPaymentProviderType())
                .confirmPayment(PaymentConfirmRequest.builder()
                        .paymentKey(request.getPaymentKey())
                        .orderId(request.getPaymentOrderId())
                        .amount(request.getPaidAmount())
                        .build());

        log.info("[*] paymentConfirmResponse => {}", paymentConfirmResponse);

        List<OrderDetailInfoResponse> orderDetailInfoResponse = orderDetailService.getOrderDetailInfoResponse(request.getOrderId());

        // 주문된 상품들에 대한 가격 계산 (구매 시점 기준 총 금액)
        BigDecimal totalPrice = orderDetailInfoResponse.stream()
                .map(orderDetailDto -> orderDetailDto.getPrice().multiply(BigDecimal.valueOf(orderDetailDto.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 결제 금액 검증
        if (totalPrice.compareTo(request.getPaidAmount()) != 0) {
            log.error("totalPrice => {}, request.getPaidAmount() => {}", totalPrice, request.getPaidAmount());
            throw new PaymentAmountMismatchException();
        }

        // 결제 정보 저장
        providerPaymentServiceFactory.get(request.getPaymentProviderType()).createPayment(
                PaymentCreationRequest.builder()
                        .orderId(request.getOrderId())
                        .totalAmount(totalPrice)
                        .providerType(request.getPaymentProviderType())
                        .providerPaymentKey(paymentConfirmResponse.getPaymentKey())
                        .providerOrderId(paymentConfirmResponse.getOrderId())
                        .providerPaymentStatus(paymentConfirmResponse.getStatus().toString())
                        .providerPaymentReceiptUrl(paymentConfirmResponse.getReceipt().getUrl())
                        .providerPaymentRequestedAt(paymentConfirmResponse.getRequestedAt())
                        .providerPaymentApprovedAt(paymentConfirmResponse.getApprovedAt())
                        .build());

        // 주문 상태 변경
        orderService.modifyOrderStatusByOrderId(request.getOrderId(), OrderStatusType.CONFIRMED);
    }
}