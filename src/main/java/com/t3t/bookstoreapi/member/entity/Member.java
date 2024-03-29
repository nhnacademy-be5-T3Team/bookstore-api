package com.t3t.bookstoreapi.member.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "members")
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;


    @JoinColumn(name = "grade_id")
    @ManyToOne
    private Grade gradeId;

    @Column(name = "member_name")
    private String name;

    @Column(name = "member_phone")
    private String phone;

    @Column(name = "member_email")
    private String email;

    @Column(name = "member_birthdate")
    private LocalDateTime birthDate;

    @Column(name = "member_latest_login")
    private LocalDateTime latestLogin;

    @Column(name = "member_point")
    private int point;

    @Column(name = "member_status")
    private String status;

    @Column(name = "member_role")
    private byte role;
}
