package com.t3t.bookstoreapi.order.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.t3t.bookstoreapi.book.model.entity.Book;
import com.t3t.bookstoreapi.order.constant.OrderStatusType;
import com.t3t.bookstoreapi.order.model.dto.OrderDetailDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.t3t.bookstoreapi.order.model.entity.OrderDetail;
import com.t3t.bookstoreapi.recommendation.model.response.BookInfoBriefResponse;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.t3t.bookstoreapi.book.model.entity.QBook.book;
import static com.t3t.bookstoreapi.book.model.entity.QBookThumbnail.bookThumbnail;
import static com.t3t.bookstoreapi.order.model.entity.QOrder.order;
import static com.t3t.bookstoreapi.order.model.entity.QOrderDetail.orderDetail;
import static com.t3t.bookstoreapi.order.model.entity.QOrderStatus.orderStatus;
import static com.t3t.bookstoreapi.order.model.entity.QPackaging.packaging;

@RequiredArgsConstructor
public class OrderDetailRepositoryCustomImpl implements OrderDetailRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    private static final QBean<OrderDetailDto> orderDetailDtoProjectionBean = Projections.bean(
            OrderDetailDto.class,
            orderDetail.id.as("id"),
            orderDetail.quantity.as("quantity"),
            orderDetail.price.as("price"),
            orderDetail.createdAt.as("createdAt"),
            orderDetail.order.id.as("orderId"),
            orderDetail.book.bookId.as("bookId"),
            orderDetail.book.bookName.as("bookName"),
            orderDetail.book.publisher.publisherName.as("bookPublisherName"),
//            orderDetail.book.bookPrice.as("bookPrice"),
//            orderDetail.book.bookDiscount.as("bookDiscount"),
            orderDetail.packaging.id.as("packagingId"),
            orderDetail.packaging.name.as("packagingName"),
            orderDetail.packaging.price.as("packagingPrice"),
            orderDetail.orderStatus.name.as("orderStatusName")
    );

    /**
     * {@inheritDoc}
     *
     * @author woody35545(구건모)
     */
    @Override
    public Optional<OrderDetailDto> getOrderDetailDtoById(long orderDetailId) {
        return Optional.ofNullable(jpaQueryFactory.select(orderDetailDtoProjectionBean)
                .from(orderDetail)
                .where(orderDetail.id.eq(orderDetailId))
                .fetchOne());
    }

    /**
     * {@inheritDoc}
     *
     * @author woody35545(구건모)
     */
    public List<OrderDetailDto> getOrderDetailDtoListByOrderId(long orderId) {
        return jpaQueryFactory.select(orderDetailDtoProjectionBean)
                .from(orderDetail)
                .where(orderDetail.order.id.eq(orderId))
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
                        OrderStatusType.DELIVERED.toString()))
                .groupBy(book.bookId, bookThumbnail.thumbnailImageUrl)
                .orderBy(orderDetail.quantity.sum().desc())
                .limit(maxCount)
                .fetch();
    }
}
