package com.t3t.bookstoreapi.participant.repository;

import com.t3t.bookstoreapi.participant.model.entity.ParticipantRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRoleRepository extends JpaRepository<ParticipantRole, Integer> {
}
