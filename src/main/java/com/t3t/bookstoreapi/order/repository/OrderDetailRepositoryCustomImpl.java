package com.t3t.bookstoreapi.order.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.t3t.bookstoreapi.book.model.entity.Book;
import com.t3t.bookstoreapi.order.model.dto.OrderDetailDto;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.t3t.bookstoreapi.order.model.entity.QOrderDetail.orderDetail;
import static com.t3t.bookstoreapi.order.model.entity.QOrderStatus.orderStatus;

@RequiredArgsConstructor
public class OrderDetailRepositoryCustomImpl implements OrderDetailRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    /**
     * {@inheritDoc}
     * @author woody35545(구건모)
     */
    @Override
    public Optional<OrderDetailDto> getOrderDetailDtoById(long orderDetailId) {
        return Optional.ofNullable(jpaQueryFactory.select(
                        Projections.bean(OrderDetailDto.class,
                                orderDetail.id.as("id"),
                                orderDetail.quantity.as("quantity"),
                                orderDetail.createdAt.as("createdAt"),
                                orderDetail.order.id.as("orderId"),
                                orderDetail.book.bookId.as("bookId"),
                                orderDetail.book.bookName.as("bookName"),
                                orderDetail.book.publisher.publisherName.as("bookPublisherName"),
                                orderDetail.book.bookPrice.as("bookPrice"),
                                orderDetail.book.bookDiscount.as("bookDiscount"),
                                orderDetail.packaging.name.as("packagingName"),
                                orderDetail.packaging.price.as("packagingPrice"),
                                orderDetail.orderStatus.name.as("orderStatusName")))
                .from(orderDetail)
                .where(orderDetail.id.eq(orderDetailId))
                .fetchOne());
    }

    @Override
    public List<Book> getSalesCountPerBook() {

        return jpaQueryFactory.select(orderDetail.book)
                .from(orderDetail)
                .join(orderDetail.orderStatus, orderStatus)
                .where(orderStatus.name.in("PENDING", "DELIVERING", "DELIVERED"))
                .groupBy(orderDetail.book.bookId)
                .orderBy(orderDetail.quantity.sum().desc())
                .fetch();
    }
}
