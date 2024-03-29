package com.t3t.bookstoreapi.member.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.persistence.*;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "member_addresses")
@Getter
public class MemberAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_address_id")
    @Comment("회원 주소 식별자")
    private Long memberAddressId;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    @Comment("대상 회원")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    @Comment("주소")
    private Address address;

    @Column(name = "member_address_nickname", length = 100, nullable = false)
    @Comment("주소 별칭")
    private String addressNickname;

    @Column(name = "member_address_detail", length = 100, nullable = false)
    @Comment("상세 주소")
    private String addressDetail;
}
