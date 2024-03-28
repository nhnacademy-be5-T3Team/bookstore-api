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
@Table(name = "paticipant_roles")
public class ParticipantRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participant_role_id")
    private Integer participantRoleId;

    @NotNull
    @Column(name = "participant_role_name")
    private String participantRoleName;

    @Builder
    public ParticipantRole(String participantRoleName) {
        this.participantRoleName = participantRoleName;
    }
}
