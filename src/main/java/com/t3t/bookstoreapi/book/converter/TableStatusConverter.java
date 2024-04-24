package com.t3t.bookstoreapi.book.converter;

import com.t3t.bookstoreapi.book.enums.TableStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;

/**
 * TableStatus 열거형과 데이터베이스의 정수 값 간의 맵핑을 담당
 */
@Convert
public class TableStatusConverter implements AttributeConverter<TableStatus, Integer> {

    /**
     * TableStatus 열거형을 데이터베이스에 저장할 때 호출되는 메서드
     *
     * @param tableStatus TableStatus 열거형 객체
     * @return 데이터베이스에 저장되는 정수 값
     * @author Yujin-nKim(김유진)
     */
    @Override
    public Integer convertToDatabaseColumn(TableStatus tableStatus) {
        return tableStatus.getCode();
    }

    /**
     * 데이터베이스에서 값을 읽어와 TableStatus 열거형으로 변환할 때 호출되는 메서드
     *
     * @param code 데이터베이스에서 읽어온 정수 값
     * @return TableStatus 열거형 객체
     * @author Yujin-nKim(김유진)
     */
    @Override
    public TableStatus convertToEntityAttribute(Integer code) {
        return TableStatus.ofCode(code);
    }
}
