package com.t3t.bookstoreapi.participant.repository;

import com.t3t.bookstoreapi.participant.model.entity.ParticipantRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRoleRepository extends JpaRepository<ParticipantRole, Integer> {
    /**
     * 페이지네이션된 참가자 역할 목록 조회
     *
     * @param pageable 페이지 정보를 담은 Pageable 객체
     * @return 페이지네이션된 참가자 역할 목록
     * @author Yujin-nKim(김유진)
     */
    Page<ParticipantRole> findAll(Pageable pageable);
}
