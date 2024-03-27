package com.t3t.bookstoreapi.member.domain;

import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "coupon_details")
@Getter
public class CouponDetail {

    @Id
    @Column(name = "coupon_id")
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Type(type = "uuid-char")
    private UUID couponId;

    @JoinColumn(name = "member_id")
    @ManyToOne
    private Member memberId;

    @Column(name = "coupon_use_date")
    private LocalDateTime useDate;

    @Column(name = "coupon_use_type")
    private String useType;

}
