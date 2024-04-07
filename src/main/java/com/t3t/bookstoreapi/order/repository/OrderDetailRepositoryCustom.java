package com.t3t.bookstoreapi.order.repository;

import com.t3t.bookstoreapi.book.model.entity.Book;

import java.util.List;

public interface OrderDetailRepositoryCustom {
    List<Book> getSalesCountPerBook();
}
