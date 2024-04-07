package com.t3t.bookstoreapi.booklike.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class BookLikeRequest {
    @NotNull(message = "bookId는 필수 입력값입니다.")
    private Long bookId;
    @NotNull(message = "memberId는 필수 입력값입니다.")
    private Long memberId;
}
