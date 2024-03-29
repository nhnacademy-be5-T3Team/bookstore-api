package com.t3t.bookstoreapi.participant.repository;

import com.t3t.bookstoreapi.participant.model.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
}
