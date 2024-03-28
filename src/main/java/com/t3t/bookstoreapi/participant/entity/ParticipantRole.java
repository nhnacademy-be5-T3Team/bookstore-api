package com.t3t.bookstoreapi.participant.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Entity
@Table(name = "participant_roles")
public class ParticipantRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participant_role_id")
    private Integer participantRoleId;

    @NotNull
    @Column(name = "participant_role_name")
    private String participantRoleName;

}
