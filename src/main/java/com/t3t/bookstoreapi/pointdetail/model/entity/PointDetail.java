package com.t3t.bookstoreapi.pointdetail.model.entity;

import com.t3t.bookstoreapi.member.model.entity.Member;
import lombok.*;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 회원의 포인트 상세 내역을 관리하는 entity class
 * 포인트의 적립 및 사용에 대한 상세한 정보를 저장
 *
 * @Author hydrationn(박수화)
 */
@Entity
@Table(name = "point_details")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_details_id")
    @Comment("포인트 내역 식별자")
    private Long pointDetailId;

    @JoinColumn(name = "member_id")
    @ManyToOne
    @Comment("회원 Id")
    private Member member;

    @Column(name = "content")
    @Comment("상세 내용")
    private String content;

    @Column(name = "point_detail_type")
    @Comment("포인트 사용/적립 구분")
    private String pointDetailType;

    @Column(name = "point_detail_date")
    @Comment("사용/적립 내역 일자")
    private LocalDateTime pointDetailDate;

    @Column(name = "point_amount")
    @Comment("포인트 양")
    private BigDecimal pointAmount;
}
