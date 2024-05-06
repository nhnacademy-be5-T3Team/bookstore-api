package com.t3t.bookstoreapi.book.model.entity;

import javax.validation.constraints.NotNull;

import com.t3t.bookstoreapi.book.converter.TableStatusConverter;
import com.t3t.bookstoreapi.book.enums.TableStatus;
import com.t3t.bookstoreapi.participant.model.entity.Participant;
import com.t3t.bookstoreapi.participant.model.entity.ParticipantRole;
import lombok.*;

import javax.persistence.*;

/**
 * 도서 참여자(participant_role_registrations) 엔티티
 *
 * @author Yujin-nKim(김유진)
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Entity
@Table(name = "participant_role_registrations")
public class ParticipantRoleRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @NotNull
    @Column(name = "is_deleted")
    @Convert(converter = TableStatusConverter.class)
    private TableStatus isDeleted;
}
