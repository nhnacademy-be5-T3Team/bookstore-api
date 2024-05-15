package com.t3t.bookstoreapi.book.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.t3t.bookstoreapi.book.exception.ConversionException;
import org.springframework.core.convert.converter.Converter;
import com.t3t.bookstoreapi.book.model.dto.ParticipantMapDto;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class StringToParticipantMapDtoConverter implements Converter<String, List<ParticipantMapDto>> {

    /**
     * 주어진 문자열을 JSON으로 변환하고, 그 JSON을 ParticipantMapDto 객체의 리스트로 변환
     *
     * @param source JSON 형식의 문자열
     * @return ParticipantMapDto 객체의 리스트
     * @throws ConversionException 변환 과정에서 발생한 예외
     * @author Yujin-nKim(김유진)
     */
    @Override
    public List<ParticipantMapDto> convert(String source) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // JSON 문자열을 List<ParticipantMapDto> 객체로 변환
            return Arrays.asList(objectMapper.readValue(source, ParticipantMapDto[].class));
        } catch (IOException e) {
            throw new ConversionException("문자열을 ParticipantMapDto 리스트로 변환하는 중에 오류가 발생했습니다.", e);
        }
    }
}