package com.t3t.bookstoreapi.member.entity;

import lombok.Getter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "member_grade_policies")
@Getter
public class GradePolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_grade_policy_id")
    private int policyId;

    @Column(name = "base_start_amount")
    private BigDecimal startAmount;

    @Column(name = "base_end_amount")
    private BigDecimal endAmount;

    @Column(name = "point_rate")
    private int rate;
}
