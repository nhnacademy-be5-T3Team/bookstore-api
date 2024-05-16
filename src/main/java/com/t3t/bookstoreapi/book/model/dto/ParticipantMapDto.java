package com.t3t.bookstoreapi.book.model.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * 도서 참여자의 id와 각 참여자의 역할의 id를 맵핑하는 클래스
 * @author Yujin-nKim(김유진)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantMapDto {
    @NotNull
    private Long participantId; // 도서 참여자 id
    @NotNull
    private Integer participantRoleId; // 도서 참여자 역할 id
}
