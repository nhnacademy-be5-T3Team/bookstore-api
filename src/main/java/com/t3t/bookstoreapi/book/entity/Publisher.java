package com.t3t.bookstoreapi.book.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Entity
@Table(name="publishers")
public class Publisher {
    @Id
    @Column(name="publisher_id")
    private Long publisherId;

    @Column(name="publisher_name")
    private String publisherName;

    @Column(name="publisher_email")
    private String publisherEmail;

}
