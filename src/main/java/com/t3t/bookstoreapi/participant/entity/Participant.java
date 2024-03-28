package com.t3t.bookstoreapi.participant.entity;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Builder
    public Participant(String participantName, String participantEmail) {
        this.participantName = participantName;
        this.participantEmail = participantEmail;
    }
}
