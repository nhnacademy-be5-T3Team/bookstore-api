package com.t3t.bookstoreapi.member.domain;

import lombok.Getter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "point_details")
@Getter
public class PointDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_detail_id")
    private Long pointDetailId;

    @JoinColumn(name = "member_id")
    @ManyToOne
    private Member memberId;

    @Column(name = "content")
    private String content;

    @Column(name = "point_detail_type")
    private String pointDetailType;

    @Column(name = "point_detail_date")
    private LocalDateTime pointDetailDate;

    @Column(name = "point_amount")
    private BigDecimal pointAmount;
}
