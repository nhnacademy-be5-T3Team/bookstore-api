package com.t3t.bookstoreapi.publisher.model.entity;

import javax.validation.constraints.NotNull;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
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
