package com.t3t.bookstoreapi.book.entity;

import com.sun.istack.NotNull;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "publishers")
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "publisher_id")
    private Long publisherId;

    @NotNull
    @Column(name = "publisher_name")
    private String publisherName;

    @NotNull
    @Column(name = "publisher_email")
    private String publisherEmail;

}
