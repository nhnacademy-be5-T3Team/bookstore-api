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
                        bookThumbnail.thumbnailImageUrl.as("thumbnailImageUrl")
                ))
                .from(orderDetail)
                .join(orderDetail.orderStatus, orderStatus)
                .leftJoin(orderDetail.book, book)
                .leftJoin(bookThumbnail).on(bookThumbnail.book.bookId.eq(book.bookId))
                .where(orderStatus.name.in("PENDING", "DELIVERING", "DELIVERED"))
                .groupBy(book.bookId, bookThumbnail.thumbnailImageUrl)
                .orderBy(orderDetail.quantity.sum().desc())
                .limit(maxCount)
                .fetch();
    }
}
