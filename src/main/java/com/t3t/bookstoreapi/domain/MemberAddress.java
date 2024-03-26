package com.t3t.bookstoreapi.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "member_addresses")
@Getter
public class MemberAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_address_id")
    private Long memberAddressId;

    @JoinColumn(name = "address_id")
    @ManyToOne
    private Member AddressId;

    @JoinColumn(name = "member_id")
    @ManyToOne
    private Addresses memberId;

    @Column(name = "members_address_nickname")
    private String addressNickname;

    @Column(name = "members_address_detail")
    private String addressDetail;
}
