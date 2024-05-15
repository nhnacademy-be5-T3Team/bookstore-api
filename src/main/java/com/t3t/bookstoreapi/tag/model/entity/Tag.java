package com.t3t.bookstoreapi.tag.model.entity;

import javax.validation.constraints.NotNull;

import com.t3t.bookstoreapi.tag.model.dto.TagDto;
import lombok.*;

import javax.persistence.*;

/**
 * 태그(tags) 엔티티
 * @author Yujin-nKim(김유진)
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Entity
@Table(name = "tags")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long tagId;

    @NotNull
    @Column(name = "tag_name")
    private String tagName;

    /**
     * 태그의 이름을 업데이트
     * @param tagName 업데이트 이름
     * @author Yujin-nKim(김유진)
     */
    public void updateTagName(String tagName) {
        this.tagName = tagName;
    }

    /**
     * Tag 객체를 TagDto 객체로 변환
     * @param tag tag 객체
     * @return TagDto 객체
     * @author Yujin-nKim(김유진)
     */
    public TagDto of(Tag tag) {
        return TagDto.builder()
                .id(tag.getTagId())
                .name(tag.getTagName())
                .build();
    }
}
