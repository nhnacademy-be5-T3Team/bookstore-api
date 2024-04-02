package com.t3t.bookstoreapi.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@NoArgsConstructor
public class BaseResponse<T> {
    private String message;
    private T data;

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

    /**
     * 응답 객체에 데이터를 설정한다.
     * @param data 응답할 데이터
     * @return BaseResponse
     * @author woody35545(구건모)
     */
    public BaseResponse<T> data(T data) {
        this.data = data;
        return this;
    }

    /**
     * 응답 객체에 메시지를 설정한다.
     * @param message 응답할 메시지
     * @return BaseResponse
     * @author woody35545(구건모)
     */
    public BaseResponse<T> message(String message) {
        this.message = message;
        return this;
    }

}
