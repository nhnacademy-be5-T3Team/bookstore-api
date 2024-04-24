package com.t3t.bookstoreapi.order.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.t3t.bookstoreapi.book.model.entity.Book;
import com.t3t.bookstoreapi.order.model.dto.OrderDetailDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.t3t.bookstoreapi.recommendation.model.response.BookInfoBriefResponse;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.t3t.bookstoreapi.book.model.entity.QBook.book;
import static com.t3t.bookstoreapi.book.model.entity.QBookThumbnail.bookThumbnail;
import static com.t3t.bookstoreapi.order.model.entity.QOrderDetail.orderDetail;
import static com.t3t.bookstoreapi.order.model.entity.QOrderStatus.orderStatus;

@RequiredArgsConstructor
public class OrderDetailRepositoryCustomImpl implements OrderDetailRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    private static final QBean<OrderDetailDto> orderDetailDtoProjectionBean = Projections.bean(
            OrderDetailDto.class,
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

    @Override
    public List<BookInfoBriefResponse> getSalesCountPerBook(int maxCount) {

        return jpaQueryFactory
                .select(Projections.fields(BookInfoBriefResponse.class,
                        book.bookId.as("id"),
                        book.bookName.as("name"),
                        bookThumbnail.thumbnailImageUrl.as("thumbnailImageUrl")))
                .from(orderDetail)
                .join(orderDetail.orderStatus, orderStatus)
                .leftJoin(orderDetail.book).fetchJoin()
                .leftJoin(bookThumbnail).on(book.bookId.eq(bookThumbnail.book.bookId))
                .where(orderStatus.name.in("PENDING", "DELIVERING", "DELIVERED"))
                .groupBy(orderDetail.book.bookId)
                .orderBy(orderDetail.quantity.sum().desc())
                .fetch();
    }
}
