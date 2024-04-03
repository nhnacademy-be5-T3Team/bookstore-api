package com.t3t.bookstoreapi.shoppingcart.model.dto;

import com.t3t.bookstoreapi.book.model.entity.Book;
import com.t3t.bookstoreapi.shoppingcart.model.entity.ShoppingCart;
import com.t3t.bookstoreapi.shoppingcart.model.entity.ShoppingCartDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartDetailDto {
    // 장바구니 상세 항목 식별자
    private Long id;

    // 책 정보
    private Book book;

    // 속한 장바구니 정보
    private ShoppingCart shoppingCart;

    // 수량
    private Long quantity;

    public static ShoppingCartDetailDto of(ShoppingCartDetail shoppingCartDetail) {
        return ShoppingCartDetailDto.builder()
                .id(shoppingCartDetail.getId())
                .book(shoppingCartDetail.getBook())
                .shoppingCart(shoppingCartDetail.getShoppingCart())
                .quantity(shoppingCartDetail.getQuantity())
                .build();
    }
}
