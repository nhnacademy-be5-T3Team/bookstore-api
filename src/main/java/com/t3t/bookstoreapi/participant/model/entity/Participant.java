package com.t3t.bookstoreapi.participant.model.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
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
