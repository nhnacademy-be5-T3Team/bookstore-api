package com.t3t.bookstoreapi.order.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.t3t.bookstoreapi.recommendation.model.response.BookInfoBriefResponse;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.t3t.bookstoreapi.book.model.entity.QBook.book;
import static com.t3t.bookstoreapi.book.model.entity.QBookThumbnail.bookThumbnail;
import static com.t3t.bookstoreapi.order.model.entity.QOrderDetail.orderDetail;
import static com.t3t.bookstoreapi.order.model.entity.QOrderStatus.orderStatus;

@RequiredArgsConstructor
public class OrderDetailRepositoryCustomImpl implements OrderDetailRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

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
