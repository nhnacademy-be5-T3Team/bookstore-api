package com.t3t.bookstoreapi.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "addresses")
@Getter
public class Addresses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long id;

    @Column(name = "roadname_address")
    private String roadNameAddress;

    @Column(name = "address_number")
    private int addressNumber;
}
