package com.t3t.bookstoreapi.book.model.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * 포장 정보에 대한 데이터 전송 객체(DTO) <br>
 * 각 객체는 포장 정보의 식별자(ID)와 이름을 가지고 있음
 * @author Yujin-nKim(김유진)
 */
@Getter
@Builder
public class PackagingDto {
    private Long id;
    private String name;
}
