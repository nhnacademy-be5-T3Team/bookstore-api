package com.t3t.bookstoreapi.member.model.entity;

import lombok.*;
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
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    @Comment("대상 회원")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", nullable = false)
    @Comment("주소")
    private Address address;

    @Column(name = "member_address_nickname", length = 100, nullable = false)
    @Comment("주소 별칭")
    private String addressNickname;

    @Column(name = "member_address_detail", length = 100, nullable = false)
    @Comment("상세 주소")
    private String addressDetail;

    @Column(name = "default_address", columnDefinition = "TINYINT(1)", nullable = false)
    @Comment("기본 주소 여부")
    private Boolean isDefaultAddress;

    /**
     * 기본 주소 설정을 한다.
     * @author woody35545(구건모)
     */
    public void asDefaultAddress() {
        this.isDefaultAddress = true;
    }

    /**
     * 기본 주소 설정을 해제한다.
     * @author woody35545(구건모)
     */
    public void asNonDefaultAddress() {
        this.isDefaultAddress = false;
    }

}
