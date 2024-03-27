package com.t3t.bookstoreapi.book.entity;

import com.sun.istack.NotNull;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "participant_role_registrations")
public class ParticipantRoleRegistration {
    @Id
    @Column(name = "participant_role_registration_id")
    private Long participantRoleRegistrationId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participant_id")
    private Participant participant;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participant_role_id")
    private ParticipantRole participantRole;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;
}
