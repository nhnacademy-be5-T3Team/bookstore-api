package com.t3t.bookstoreapi.member.domain;

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
@Table(name = "addresses")
@Getter
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    @Comment("주소 식별자")
    private Long id;

    @Column(name = "roadname_address", length = 100, nullable = false)
    @Comment("도로명 주소")
    private String roadNameAddress;

    @Column(name = "address_number", nullable = false)
    @Comment("우편 주소")
    private int addressNumber;
}
