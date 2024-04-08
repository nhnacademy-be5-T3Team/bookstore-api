package com.t3t.bookstoreapi.member.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Table(name = "member_grades")
public class MemberGrade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_grade_id", columnDefinition = "TINYINT")
    @Comment("회원 등급 식별자")
    private int gradeId;

    @Column(name = "member_grade_name", length = 30, nullable = false)
    @Comment("회원 등급 이름")
    private String name;

    @ManyToOne
    @JoinColumn(name = "member_grade_policy_id")
    @Comment("회원 등급 정책 식별자")
    private MemberGradePolicy policy;
}
