package com.t3t.bookstoreapi.book.entity;

import com.sun.istack.NotNull;
import lombok.Getter;

import javax.persistence.*;

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
}
