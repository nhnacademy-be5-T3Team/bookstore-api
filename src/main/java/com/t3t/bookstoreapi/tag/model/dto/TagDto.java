package com.t3t.bookstoreapi.tag.model.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TagDto {
    private Long tagId;
    private String tagName;
}