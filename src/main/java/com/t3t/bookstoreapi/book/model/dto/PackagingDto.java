package com.t3t.bookstoreapi.book.model.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PackagingDto {
    private Long id;
    private String name;
}
