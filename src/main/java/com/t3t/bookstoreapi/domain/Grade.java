package com.t3t.bookstoreapi.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "member_grades")
@Getter
public class Grade {

    @Id
    @Column(name = "member_grade_id")
    private byte gradeId;

    @Column(name = "member_grade_name")
    private String name;

    @JoinColumn(name = "member_grade_policy_id")
    @ManyToOne
    private GradePolicy policyId;

}
