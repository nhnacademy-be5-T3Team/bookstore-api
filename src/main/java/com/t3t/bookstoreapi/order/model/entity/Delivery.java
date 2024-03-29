package com.t3t.bookstoreapi.order.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 배송 정보를 나타내는 엔티티
 * @author woody35545(구건모)
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "deliveries")
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    @Comment("배송 식별자")
    private Long id;

    @Column(name = "delivery_price", nullable = false)
    @Comment("배송비")
    private BigDecimal price;

    @Column(name = "delivery_address_number")
    @Comment("배송 우편 주소")
    private int addressNumber;

    @Column(name = "delivery_roadname_address", length = 100)
    @Comment("배송 도로명 주소")
    private String roadnameAddress;

    @Column(name = "delivery_detail_address", length = 100, nullable = false)
    @Comment("배송 상세 주소")
    private String detailAddress;

    @Column(name = "delivery_date", nullable = false)
    @Comment("배송 일자")
    private LocalDate deliveryDate;

    @Column(name = "delivery_recipient_name", length = 30, nullable = false)
    @Comment("배송 수령인 이름")
    private String recipientName;

    @Column(name = "delivery_recipient_phone", length = 13, nullable = false)
    @Comment("배송 수령인 전화번호")
    private String recipientPhoneNumber;

}
