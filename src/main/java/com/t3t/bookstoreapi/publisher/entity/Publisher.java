package com.t3t.bookstoreapi.publisher.entity;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Builder
    public Publisher(String publisherName, String publisherEmail) {
        this.publisherName = publisherName;
        this.publisherEmail = publisherEmail;
    }
}
