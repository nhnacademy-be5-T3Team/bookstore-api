package com.t3t.bookstoreapi.coupon.model.entity;

import com.t3t.bookstoreapi.member.model.entity.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Builder
@Getter@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity@Table(name = "coupon_details")
public class CouponDetail {
    @Id
    @Column(name = "coupon_id")
    private String couponId;
    @Column(name = "coupon_use_date")
    private LocalDate couponUseDate;
    @Column(name = "coupon_use_type")
    private String couponUseType;
    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
}
