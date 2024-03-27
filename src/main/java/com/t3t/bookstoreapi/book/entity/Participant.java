package com.t3t.bookstoreapi.book.entity;

import com.sun.istack.NotNull;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "participants")
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participant_id")
    private Long participantId;

    @NotNull
    @Column(name = "participant_name")
    private String participantName;

    @NotNull
    @Column(name = "participant_email")
    private String participantEmail;
}
