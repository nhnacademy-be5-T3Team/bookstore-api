package com.t3t.bookstoreapi.participant.model.dto;

import com.t3t.bookstoreapi.participant.model.entity.ParticipantRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 도서 참여자 역할에 대한 데이터 전송 객체(DTO) <br>
 * 각 객체는 도서 참여자 역할의 식별자(ID)와 영어 이름, 한국어 이름을 가지고 있음
 * @author Yujin-nKim(김유진)
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantRoleDto {
    private Integer id;
    private String roleNameEn;
    private String roleNameKr;

    /**
     * ParticipantRole 엔티티를 ParticipantRoleDto 객체로 변환
     * @param participantRole ParticipantRole 엔티티
     * @return ParticipantRoleDto 객체
     * @author Yujin-nKim(김유진)
     */
    public static ParticipantRoleDto of(ParticipantRole participantRole) {
        return ParticipantRoleDto.builder()
                .id(participantRole.getParticipantRoleId())
                .roleNameKr(participantRole.getParticipantRoleNameKr())
                .roleNameEn(participantRole.getParticipantRoleNameEn())
                .build();
    }
}
