package com.t3t.bookstoreapi.book.repository;

import com.t3t.bookstoreapi.book.model.entity.ParticipantRoleRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipantRoleRegistrationRepository extends JpaRepository<ParticipantRoleRegistration, Long> {
    /**
     * 특정 도서에 속한 참여자 역할 등록 정보 목록을 조회
     * @param bookId 도서 식별자
     * @return 해당 도서에 속한 참여자 역할 등록 정보 목록
     * @author Yujin-nKim(김유진)
     */
    List<ParticipantRoleRegistration> findByBookBookId(Long bookId);
}
