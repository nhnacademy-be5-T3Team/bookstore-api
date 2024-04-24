package com.t3t.bookstoreapi.book.model.dto;

import lombok.*;

import java.util.List;

/**
 * 특정 도서 ID와 해당 도서에 연관된 참여자 역할 등록 정보 목록을 포함하는 DTO
 * @author Yujin-nKim(김유진)
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantRoleRegistrationDtoByBookId {
    private Long bookId;
    private List<ParticipantRoleRegistrationDto> participantList;
}
