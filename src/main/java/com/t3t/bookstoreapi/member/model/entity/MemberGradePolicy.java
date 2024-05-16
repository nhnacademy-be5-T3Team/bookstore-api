package com.t3t.bookstoreapi.member.model.entity;

import lombok.*;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 회원 등금 정책 를 관리하는 Entity class
 * 회원 등급 정책에 대한 상세한 정보 저장
 *
 * @Author hydrationn(박수화)
 */
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

    /**
     * 회원 등급 정책 변경에 따른 기준 시작 금액 업데이트 처리
     * @param updateStartAmount
     * @author hydrationn(박수화)
     */
    public void updateStartAmount(BigDecimal updateStartAmount) {
        this.startAmount = updateStartAmount;
    }

    /**
     * 회원 등급 정책 변경에 따른 기준 종료 금액 업데이트 처리
     * @param updateEndAmount
     * @author hydrationn(박수화)
     */
    public void updateEndAmount(BigDecimal updateEndAmount) {
        this.endAmount = updateEndAmount;
    }

    /**
     * 회원 등급 정책 변경에 따른 포인트 적립 비율 업데이트 처리
     * @param updateRate
     * @author hydrationn(박수화)
     */
    public void updateRate(int updateRate) {
        this.rate = updateRate;
    }
}
