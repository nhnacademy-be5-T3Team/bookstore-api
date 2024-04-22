package com.t3t.bookstoreapi.order.repository;

import com.t3t.bookstoreapi.recommendation.model.response.BookInfoBriefResponse;

import java.util.List;

public interface OrderDetailRepositoryCustom {
    List<BookInfoBriefResponse> getSalesCountPerBook(int maxCount);
}
