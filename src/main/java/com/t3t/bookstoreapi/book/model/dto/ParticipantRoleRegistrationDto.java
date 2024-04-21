package com.t3t.bookstoreapi.book.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 도서 참여자에 대한 데이터 전송 객체(DTO) <br>
 * 각 객체는 도서 참여자의 식별자(ID)와 이름, 역할을 가지고 있음
 * @author Yujin-nKim(김유진)
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantRoleRegistrationDto {
    private int id;
    private String name;
    private String role;
}
