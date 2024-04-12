package com.t3t.bookstoreapi.book.model.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PackagingInfo {
    private Long id;
    private String name;
}
