package com.t3t.bookstoreapi.booklike.model.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BookLikeRequest {
    private Long bookId;
    private Long memberId;
}
