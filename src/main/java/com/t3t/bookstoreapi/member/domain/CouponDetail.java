package com.t3t.bookstoreapi.member.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "coupon_details")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CouponDetail {

    @Id
    @Column(name = "coupon_id")
    private String couponId;

    @JoinColumn(name = "member_id")
    @ManyToOne
    private Member member;

    @Column(name = "coupon_use_date")
    private LocalDateTime useDate;

    @Column(name = "coupon_use_type")
    private String useType;

}
