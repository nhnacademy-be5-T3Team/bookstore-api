package com.t3t.bookstoreapi.member.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.math.BigDecimal;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "member_grade_policies")
public class MemberGradePolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_grade_policy_id")
    @Comment("회원 등급 정책 식별자")
    private Long policyId;

    @Column(name = "base_start_amount", nullable = false)
    @Comment("기준 시작 금액")
    private BigDecimal startAmount;

    @Column(name = "base_end_amount", nullable = false)
    @Comment("기준 종료 금액")
    private BigDecimal endAmount;

    @Column(name = "point_rate", nullable = false)
    @Comment("포인트 적립 비율")
    private int rate;
}
