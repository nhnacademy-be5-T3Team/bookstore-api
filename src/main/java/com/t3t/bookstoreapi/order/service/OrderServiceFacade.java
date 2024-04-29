package com.t3t.bookstoreapi.order.service;

import com.t3t.bookstoreapi.book.exception.BookNotFoundForIdException;
import com.t3t.bookstoreapi.book.model.entity.Book;
import com.t3t.bookstoreapi.book.repository.BookRepository;
import com.t3t.bookstoreapi.order.constant.OrderStatusType;
import com.t3t.bookstoreapi.order.exception.OrderStatusNotFoundForNameException;
import com.t3t.bookstoreapi.order.exception.PackagingNotFoundForIdException;
import com.t3t.bookstoreapi.order.exception.PaymentAmountMismatchException;
import com.t3t.bookstoreapi.order.model.dto.DeliveryDto;
import com.t3t.bookstoreapi.order.model.dto.GuestOrderDto;
import com.t3t.bookstoreapi.order.model.dto.OrderDetailDto;
import com.t3t.bookstoreapi.order.model.dto.OrderDto;
import com.t3t.bookstoreapi.order.model.entity.GuestOrder;
import com.t3t.bookstoreapi.order.model.entity.Packaging;
import com.t3t.bookstoreapi.order.model.request.*;
import com.t3t.bookstoreapi.order.model.response.GuestOrderPreparationResponse;
import com.t3t.bookstoreapi.order.model.response.MemberOrderPreparationResponse;
import com.t3t.bookstoreapi.order.repository.GuestOrderRepository;
import com.t3t.bookstoreapi.order.repository.OrderDetailRepository;
import com.t3t.bookstoreapi.order.repository.OrderStatusRepository;
import com.t3t.bookstoreapi.order.repository.PackagingRepository;
import com.t3t.bookstoreapi.payment.model.request.PaymentConfirmRequest;
import com.t3t.bookstoreapi.payment.model.request.PaymentCreationRequest;
import com.t3t.bookstoreapi.payment.model.response.PaymentConfirmResponse;
import com.t3t.bookstoreapi.payment.service.ProviderPaymentServiceFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
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
        DeliveryDto deliveryDto = deliveryService.createDelivery(DeliveryCreationRequest.builder()
                .price(DEFAULT_DELIVERY_PRICE)
                .detailAddress(memberOrderPreparationRequest.getDetailAddress())
                .addressNumber(memberOrderPreparationRequest.getAddressNumber())
                .roadnameAddress(memberOrderPreparationRequest.getRoadnameAddress())
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

}