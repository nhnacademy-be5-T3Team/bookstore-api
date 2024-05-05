package com.t3t.bookstoreapi.book.repository;

import com.t3t.bookstoreapi.book.model.entity.ParticipantRoleRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipantRoleRegistrationRepository extends JpaRepository<ParticipantRoleRegistration, Long> {
    List<ParticipantRoleRegistration> findByBookBookId(Long bookId);
}
