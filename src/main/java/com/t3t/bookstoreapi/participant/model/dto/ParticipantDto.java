package com.t3t.bookstoreapi.participant.model.dto;

import com.t3t.bookstoreapi.participant.model.entity.Participant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 도서 참여자에 대한 데이터 전송 객체(DTO) <br>
 * 각 객체는 도서 참여자의 식별자(ID)와 이름, 이메일을 가지고 있음
 * @author Yujin-nKim(김유진)
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantDto {
    private Long id;
    private String name;
    private String email;

    /**
     * Participant 엔티티를 ParticipantDto 객체로 변환
     * @param participant Participant 엔티티
     * @return ParticipantDto 객체
     * @author Yujin-nKim(김유진)
     */
    public static ParticipantDto of(Participant participant) {
        return ParticipantDto.builder()
                .id(participant.getParticipantId())
                .name(participant.getParticipantName())
                .email(participant.getParticipantEmail())
                .build();
    }
}
