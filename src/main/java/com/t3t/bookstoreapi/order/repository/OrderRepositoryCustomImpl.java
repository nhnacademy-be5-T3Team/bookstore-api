package com.t3t.bookstoreapi.order.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.t3t.bookstoreapi.order.model.response.OrderInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.t3t.bookstoreapi.book.model.entity.QBook.book;
import static com.t3t.bookstoreapi.book.model.entity.QBookImage.bookImage;
import static com.t3t.bookstoreapi.member.model.entity.QMember.member;
import static com.t3t.bookstoreapi.order.model.entity.QDelivery.delivery;
import static com.t3t.bookstoreapi.order.model.entity.QOrder.order;
import static com.t3t.bookstoreapi.order.model.entity.QOrderDetail.orderDetail;
import static com.t3t.bookstoreapi.order.model.entity.QOrderStatus.orderStatus;
import static com.t3t.bookstoreapi.order.model.entity.QPackaging.packaging;
import static com.t3t.bookstoreapi.payment.model.entity.QPayment.payment;
import static com.t3t.bookstoreapi.payment.model.entity.QPaymentProvider.paymentProvider;
import static com.t3t.bookstoreapi.payment.model.entity.QTossPayment.tossPayment;
import static com.t3t.bookstoreapi.publisher.model.entity.QPublisher.publisher;

@RequiredArgsConstructor
public class OrderRepositoryCustomImpl implements OrderRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    /**
     * {@inheritDoc}
     *
     * @author woody35545(구건모)
     */
    @Override
    public Page<OrderInfoResponse> getOrderInfoResponseByMemberIdWithPaging(Long memberId, Pageable pageable) {
        QueryResults<OrderInfoResponse> queryResultList =
                queryFactory.selectDistinct(Projections.fields(
                                OrderInfoResponse.class,
                                order.id.as("orderId"),
                                order.createdAt.as("orderCreatedAt"),
                                member.id.as("memberId"),
                                payment.id.as("paymentId"),
                                paymentProvider.id.as("paymentProviderId"),
                                paymentProvider.name.as("paymentProviderType"),
                                payment.totalAmount.as("paymentTotalAmount"),
                                payment.createdAt.as("paymentCreatedAt"),
                                tossPayment.tossOrderId.as("paymentProviderOrderId")
                        ))
                        .from(order)
                            .join(order.member, member)
                            .leftJoin(payment).on(payment.order.id.eq(order.id))
                            .leftJoin(payment.paymentProvider, paymentProvider)
                        // TODO : 추후 결제 수단에 따라 변경 되도록 수정 예정
                            .leftJoin(tossPayment).on(tossPayment.id.eq(payment.id))
                        .where(member.id.eq(memberId))
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetchResults();

        List<OrderInfoResponse> orderInfoResponseList = queryResultList.getResults();

        for (OrderInfoResponse orderInfoResponse : orderInfoResponseList) {
            List<OrderInfoResponse.OrderDetailInfo> orderDetailInfoList =
                    queryFactory.selectDistinct(Projections.fields(
                                    OrderInfoResponse.OrderDetailInfo.class,
                                    orderDetail.id.as("orderDetailId"),
                                    orderDetail.quantity.as("quantity"),
                                    orderDetail.price.as("price"),
                                    order.id.as("orderId"),
                                    book.bookId.as("bookId"),
                                    book.bookName.as("bookName"),
                                    bookImage.bookImageUrl.as("bookImageUrl"),
                                    publisher.publisherName.as("bookPublisherName"),
                                    packaging.id.as("packagingId"),
                                    packaging.name.as("packagingName"),
                                    packaging.price.as("packagingPrice"),
                                    orderStatus.name.as("orderStatusName"),
                                    delivery.id.as("deliveryId"),
                                    delivery.price.as("deliveryPrice"),
                                    delivery.addressNumber.as("addressNumber"),
                                    delivery.roadnameAddress.as("roadnameAddress"),
                                    delivery.detailAddress.as("detailAddress"),
                                    delivery.deliveryDate.as("deliveryDate"),
                                    delivery.recipientName.as("recipientName"),
                                    delivery.recipientPhoneNumber.as("recipientPhoneNumber")
                            ))
                            .from(orderDetail)
                                .join(orderDetail.order, order)
                                .join(orderDetail.book, book)
                                .join(book.publisher, publisher)
                                .leftJoin(bookImage).on(bookImage.book.bookId.eq(book.bookId))
                                .join(order.delivery, delivery)
                                .leftJoin(orderDetail.packaging, packaging)
                                .join(orderDetail.orderStatus, orderStatus)
                            .where(orderDetail.order.id.eq(orderInfoResponse.getOrderId()))
                            .fetch();

            List<OrderInfoResponse.OrderDetailInfo> uniqueOrderDetailInfoList = new ArrayList<>();
            Set<Long> seenOrderDetailIds = new HashSet<>();

            for (OrderInfoResponse.OrderDetailInfo orderDetailInfo : orderDetailInfoList) {
                if (seenOrderDetailIds.add(orderDetailInfo.getOrderDetailId())) {
                    uniqueOrderDetailInfoList.add(orderDetailInfo);
                }
            }

            orderInfoResponse.setOrderDetailInfoList(uniqueOrderDetailInfoList);
        }

        return new PageImpl<>(orderInfoResponseList, pageable, queryResultList.getTotal());
    }
}
