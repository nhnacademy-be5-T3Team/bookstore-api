package com.t3t.bookstoreapi.order.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.t3t.bookstoreapi.book.enums.TableStatus;
import com.t3t.bookstoreapi.book.model.entity.Book;
import com.t3t.bookstoreapi.order.constant.OrderStatusType;
import com.t3t.bookstoreapi.order.model.dto.OrderDetailDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.t3t.bookstoreapi.order.model.entity.OrderDetail;
import com.t3t.bookstoreapi.order.model.response.OrderDetailInfoResponse;
import com.t3t.bookstoreapi.recommendation.model.response.BookInfoBriefResponse;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.t3t.bookstoreapi.book.model.entity.QBook.book;
import static com.t3t.bookstoreapi.book.model.entity.QBookThumbnail.bookThumbnail;
import static com.t3t.bookstoreapi.order.model.entity.QDelivery.delivery;
import static com.t3t.bookstoreapi.order.model.entity.QOrder.order;
import static com.t3t.bookstoreapi.order.model.entity.QOrderDetail.orderDetail;
import static com.t3t.bookstoreapi.order.model.entity.QOrderStatus.orderStatus;
import static com.t3t.bookstoreapi.order.model.entity.QPackaging.packaging;
import static com.t3t.bookstoreapi.publisher.model.entity.QPublisher.publisher;

@RequiredArgsConstructor
public class OrderDetailRepositoryCustomImpl implements OrderDetailRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    /**
     * {@inheritDoc}
     *
     * @author woody35545(구건모)
     */
    @Override
    public Optional<OrderDetailDto> getOrderDetailDtoById(long orderDetailId) {
        return Optional.ofNullable(jpaQueryFactory.select(Projections.bean(
                        OrderDetailDto.class,
                        orderDetail.id.as("id"),
                        orderDetail.quantity.as("quantity"),
                        orderDetail.price.as("price"),
                        orderDetail.createdAt.as("createdAt"),
                        orderDetail.order.id.as("orderId"),
                        book.bookId.as("bookId"),
                        book.bookName.as("bookName"),
                        book.publisher.publisherName.as("bookPublisherName"),
                        packaging.id.as("packagingId"),
                        packaging.name.as("packagingName"),
                        packaging.price.as("packagingPrice"),
                        orderStatus.name.as("orderStatusName")))
                .from(orderDetail)
                .join(orderDetail.book, book)
                .join(book.publisher, publisher)
                .join(orderDetail.orderStatus, orderStatus)
                .leftJoin(orderDetail.packaging, packaging)
                .where(orderDetail.id.eq(orderDetailId))
                .fetchOne());
    }

    /**
     * {@inheritDoc}
     *
     * @author woody35545(구건모)
     */
    public List<OrderDetailInfoResponse> getOrderDetailInfoResponseListByOrderId(long orderId) {
        return jpaQueryFactory.select(
                        Projections.fields(
                                OrderDetailInfoResponse.class,
                                orderDetail.id.as("id"),
                                orderDetail.quantity.as("quantity"),
                                orderDetail.price.as("price"),
                                orderDetail.createdAt.as("createdAt"),
                                orderDetail.order.id.as("orderId"),
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
                                delivery.deliveryDate.as("deliveryDate"),
                                delivery.recipientName.as("recipientName"),
                                delivery.recipientPhoneNumber.as("recipientPhoneNumber")))
                .join(orderDetail.book, book)
                .join(book.publisher, publisher)
                .join(orderDetail.orderStatus, orderStatus)
                .join(order.delivery, delivery)
                .leftJoin(orderDetail.packaging, packaging)
                .where(order.id.eq(orderId))
                .fetch();
    }

    /**
     * {@inheritDoc}
     *
     * @author woody35545(구건모)
     */

    @Override
    public List<OrderDetail> getOrderDetailListByOrderId(long orderId) {
        return jpaQueryFactory.selectFrom(orderDetail)
                .join(orderDetail.order).fetchJoin()
                .join(orderDetail.book).fetchJoin()
                .leftJoin(orderDetail.packaging).fetchJoin()
                .join(orderDetail.orderStatus).fetchJoin()
                .where(orderDetail.order.id.eq(orderId))
                .fetch();
    }

    @Override
    public List<BookInfoBriefResponse> getSalesCountPerBook(int maxCount) {

        return jpaQueryFactory
                .select(Projections.fields(BookInfoBriefResponse.class,
                        book.bookId.as("id"),
                        book.bookName.as("name"),
                        bookThumbnail.thumbnailImageUrl.as("thumbnailImageUrl")))
                .from(orderDetail)
                .join(orderDetail.orderStatus, orderStatus)
                .leftJoin(orderDetail.book, book)
                .leftJoin(bookThumbnail).on(bookThumbnail.book.bookId.eq(book.bookId))
                .where(orderStatus.name.in(
                                OrderStatusType.PENDING.toString(),
                                OrderStatusType.DELIVERING.toString(),
                                OrderStatusType.DELIVERED.toString())
                        .and(book.isDeleted.eq(TableStatus.FALSE)))
                .groupBy(book.bookId, bookThumbnail.thumbnailImageUrl)
                .orderBy(orderDetail.quantity.sum().desc())
                .limit(maxCount)
                .fetch();
    }
}
