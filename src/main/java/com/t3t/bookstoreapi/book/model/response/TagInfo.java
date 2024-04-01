package com.t3t.bookstoreapi.book.model.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TagInfo {
    private int id;
    private String name;
}
