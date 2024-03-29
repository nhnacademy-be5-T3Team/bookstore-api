package com.t3t.bookstoreapi.shoppingcart.model.entity;

import com.t3t.bookstoreapi.book.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "shopping_cart_details")
public class ShoppingCartDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shopping_cart_detail_id")
    @Comment("장바구니 상세 항목 식별자")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    @Comment("장바구니에 담긴 책")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shopping_cart_id", nullable = false)
    @Comment("장바구니 상세 항목이 속한 장바구니")
    private ShoppingCart shoppingCart;

    @Column(name = "book_quantity", nullable = false)
    @Comment("책 수량")
    private Long quantity;
}
