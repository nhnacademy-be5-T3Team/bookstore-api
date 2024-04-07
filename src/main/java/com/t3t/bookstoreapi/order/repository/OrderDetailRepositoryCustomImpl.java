package com.t3t.bookstoreapi.order.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.t3t.bookstoreapi.book.model.entity.Book;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.t3t.bookstoreapi.order.model.entity.QOrderDetail.orderDetail;
import static com.t3t.bookstoreapi.order.model.entity.QOrderStatus.orderStatus;

@RequiredArgsConstructor
public class OrderDetailRepositoryCustomImpl implements OrderDetailRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

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
