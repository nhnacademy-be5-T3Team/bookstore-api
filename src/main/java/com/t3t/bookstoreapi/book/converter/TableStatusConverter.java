package com.t3t.bookstoreapi.book.converter;

import com.t3t.bookstoreapi.book.enums.TableStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;

@Convert
public class TableStatusConverter implements AttributeConverter<TableStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(TableStatus tableStatus) {
        return tableStatus.getCode();
    }

    @Override
    public TableStatus convertToEntityAttribute(Integer code) {
        return TableStatus.ofCode(code);
    }
}
