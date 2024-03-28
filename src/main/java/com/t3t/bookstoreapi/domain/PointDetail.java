package com.t3t.bookstoreapi.domain;

import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "point_details")
@Getter
public class PointDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_details_id")
    private Long pointDetailId;

    @JoinColumn(name = "member_id")
    @ManyToOne
    private Member memberId;

    @Column(name = "content")
    private String content;

    @Column(name = "point_detail_type")
    private String PointDetailType;

    @Column(name = "point_detail_date")
    private LocalDateTime PointDetailDate;

    @Column(name = "point_amount")
    private BigDecimal pointAmount;
}
