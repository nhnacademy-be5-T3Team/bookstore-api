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

import java.util.List;

import static com.t3t.bookstoreapi.book.model.entity.QBook.book;
import static com.t3t.bookstoreapi.member.model.entity.QMember.member;
import static com.t3t.bookstoreapi.order.model.entity.QDelivery.delivery;
import static com.t3t.bookstoreapi.order.model.entity.QOrder.order;
import static com.t3t.bookstoreapi.order.model.entity.QOrderDetail.orderDetail;
import static com.t3t.bookstoreapi.order.model.entity.QOrderStatus.orderStatus;
import static com.t3t.bookstoreapi.order.model.entity.QPackaging.packaging;
import static com.t3t.bookstoreapi.payment.model.entity.QPayment.payment;
import static com.t3t.bookstoreapi.payment.model.entity.QPaymentProvider.paymentProvider;
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
                queryFactory.select(Projections.fields(
                                OrderInfoResponse.class,
                                order.id.as("orderId"),
                                order.createdAt.as("orderCreatedAt"),
                                member.id.as("memberId"),
                                payment.id.as("paymentId"),
                                paymentProvider.id.as("paymentProviderId"),
                                paymentProvider.name.as("paymentProviderType"),
                                payment.totalAmount.as("paymentTotalAmount"),
                                payment.createdAt.as("paymentCreatedAt")))
                        .from(order)
                            .join(order.member, member)
                            .leftJoin(payment).on(payment.order.id.eq(order.id))
                            .leftJoin(payment.paymentProvider, paymentProvider)
                        .where(member.id.eq(memberId))
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetchResults();

        List<OrderInfoResponse> orderInfoResponseList = queryResultList.getResults();

        for (OrderInfoResponse orderInfoResponse : orderInfoResponseList) {

            List<OrderInfoResponse.OrderDetailInfo> orderDetailInfoList =
                    queryFactory.select(Projections.fields(
                                    OrderInfoResponse.OrderDetailInfo.class,
                                    orderDetail.id.as("orderDetailId"),
                                    orderDetail.quantity.as("quantity"),
                                    orderDetail.price.as("price"),
                                    order.id.as("orderId"),
                                    book.bookId.as("bookId"),
                                    book.bookName.as("bookName"),
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
                                .join(order.delivery, delivery)
                                .leftJoin(orderDetail.packaging, packaging)
                                .join(orderDetail.orderStatus, orderStatus)
                            .where(orderDetail.order.id.eq(orderInfoResponse.getOrderId()))
                            .fetch();

            orderInfoResponse.setOrderDetailInfoList(orderDetailInfoList);
        }

        return new PageImpl<>(orderInfoResponseList, pageable, queryResultList.getTotal());
    }
}
