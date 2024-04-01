package com.t3t.bookstoreapi.book.model.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthorInfo {
    private String name;
    private String role;
}
