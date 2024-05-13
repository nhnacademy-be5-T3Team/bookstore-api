package com.t3t.bookstoreapi.order.model.entity;

import com.t3t.bookstoreapi.book.model.entity.Book;
import lombok.*;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 주문 상세 엔티티
 * @author woody35545(구건모)
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "order_details")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_detail_id")
    @Comment("주문 상세 식별자")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    @Comment("주문한 책")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "package_id")
    @Comment("주문 상세 항목에 사용된 포장지")
    private Packaging packaging;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    @Comment("주문 상세 항목이 속한 주문 정보")
    private Order order;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_status_id", nullable = false)
    @Comment("주문 상태")
    private OrderStatus orderStatus;

    @Column(name = "order_detail_created_at", nullable = false)
    @Comment("주문 상세 생성 일시")
    private LocalDateTime createdAt;

    @Column(name = "order_quantity", nullable = false)
    @Comment("주문 수량")
    private Integer quantity;

    /**
     * 주문 상세 단건 가격<br>
     * 주문 요청 시점의 책 가격과 할인율 등 결제 금액에 영향을 주는 요인들을 고려해서 산출된 금액으로<br>
     * 최종적으로 사용자가 결제해야하는 상품 단건 가격을 의미한다.
     */
    @Column(name = "order_detail_price", nullable = false)
    @Comment("주문 상세 단건 가격")
    private BigDecimal price;
}
