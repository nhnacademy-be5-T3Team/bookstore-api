package com.t3t.bookstoreapi.book.model.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RecommendedBooksResponse {
    private String name; // 도서 제목
    private String coverImageUrl; // 도서 커버 이미지 url
}
