package com.t3t.bookstoreapi.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class BaseResponse<T> {
    private final String message;
    private final T data;

    @Builder
    public BaseResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public static <T> BaseResponse<T> success(String message, T data) {
        return BaseResponse.<T>builder()
                .message(message)
                .data(data)
                .build();
    }
}
