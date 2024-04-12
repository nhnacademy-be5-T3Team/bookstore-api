package com.t3t.bookstoreapi.book.controller.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum TableStatus {
    TRUE(true, 1),
    FALSE(false, 0);

    private boolean value;
    private Integer code;

    public static TableStatus ofCode(Integer code) {
        return Arrays.stream(TableStatus.values())
                .filter(v -> v.getCode().equals(code))
                .findAny()
                .orElseThrow(() -> new RuntimeException(String.format("상태 코드에 code=[%s]가 존재하지 않습니다", code)));
    }
}