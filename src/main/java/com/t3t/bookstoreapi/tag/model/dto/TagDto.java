package com.t3t.bookstoreapi.tag.model.dto;

import com.t3t.bookstoreapi.tag.model.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 태그에 대한 데이터 전송 객체(DTO) <br>
 * 각 객체는 태그의 식별자(ID)와 이름을 가지고 있음
 * @author Yujin-nKim(김유진)
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TagDto {
    private Long id;
    private String name;

    /**
     * Tag 엔티티를 TagDto 객체로 변환
     * @param tag Tag 엔티티
     * @return TagDto 객체
     * @author Yujin-nKim(김유진)
     */
    public static TagDto of(Tag tag) {
        return TagDto.builder()
                .id(tag.getTagId())
                .name(tag.getTagName())
                .build();
    }
}
