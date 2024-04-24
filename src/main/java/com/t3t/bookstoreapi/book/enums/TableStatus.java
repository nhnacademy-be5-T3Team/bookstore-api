package com.t3t.bookstoreapi.book.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * TableStatus는 데이터베이스에서 사용되는 특정 테이블의 상태를 나타내는 열거형
 * 각 상태는 데이터베이스에 저장되는 정수 코드와 해당 상태의 부울 값을 가지고 있음
 */
@AllArgsConstructor
@Getter
public enum TableStatus {
    TRUE(true, 1),
    FALSE(false, 0);

    private boolean value; // 상태를 나타내는 부울 값
    private Integer code; // 데이터베이스에 저장되는 정수 코드

    /**
     * 데이터베이스에서 읽어온 정수 코드를 해당하는 TableStatus 값으로 변환하는 정적 메서드
     *
     * @param code 데이터베이스에서 읽어온 정수 코드
     * @return 주어진 코드에 해당하는 TableStatus 값
     * @throws RuntimeException 주어진 코드와 일치하는 TableStatus가 없을 경우 예외가 발생
     * @author Yujin-nKim(김유진)
     */
    public static TableStatus ofCode(Integer code) {
        return Arrays.stream(TableStatus.values())
                .filter(v -> v.getCode().equals(code))
                .findAny()
                .orElseThrow(() -> new RuntimeException(String.format("상태 코드에 code=[%s]가 존재하지 않습니다", code)));
    }
}