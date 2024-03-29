package com.t3t.bookstoreapi.order.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 책 포장지 엔티티
 * @Author woody35545(구건모)
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "packages")
public class Packaging {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "package_id")
    @Comment("포장지 식별자")
    private Long id;

    @Column(name = "package_name", length = 50, nullable = false)
    @Comment("포장지 이름")
    private String name;

    @Column(name = "package_price", nullable = false)
    @Comment("포장지 단가")
    private BigDecimal price;

}
