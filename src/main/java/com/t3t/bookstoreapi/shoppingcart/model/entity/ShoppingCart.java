package com.t3t.bookstoreapi.shoppingcart.model.entity;

import com.t3t.bookstoreapi.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import javax.persistence.*;

/**
 * 장바구니 엔티티
 * @author woody35545(구건모)
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "shopping_carts")
public class ShoppingCart {
    @Id
    @Column(name = "shopping_cart_id")
    @Comment("장바구니 식별자")
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id", nullable = false)
    @Comment("장바구니 소유 회원")
    private Member member;
}
