package com.t3t.bookstoreapi.order.repository;

import com.t3t.bookstoreapi.book.model.entity.Book;
import com.t3t.bookstoreapi.order.model.dto.OrderDetailDto;

import java.util.List;
import java.util.Optional;

public interface OrderDetailRepositoryCustom {
    /**
     * 주문 상세 식별자로 주문 상세 DTO 조회
     *
     * @author woody35545(구건모)
     */
    Optional<OrderDetailDto> getOrderDetailDtoById(long orderDetailId);

    List<Book> getSalesCountPerBook();
}
