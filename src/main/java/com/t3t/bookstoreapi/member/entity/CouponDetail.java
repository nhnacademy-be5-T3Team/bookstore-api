package com.t3t.bookstoreapi.member.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "coupon_details")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponDetail {

    @Id
    @Column(name = "coupon_id")
    private String couponId;

    @JoinColumn(name = "member_id")
    @ManyToOne
    private Member memberId;

    @Column(name = "coupon_use_date")
    private LocalDateTime useDate;

    @Column(name = "coupon_use_type")
    private String useType;
}
