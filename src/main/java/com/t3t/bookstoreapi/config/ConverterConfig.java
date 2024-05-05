package com.t3t.bookstoreapi.config;

import com.t3t.bookstoreapi.book.converter.StringToParticipantMapDtoConverter;
import com.t3t.bookstoreapi.book.model.dto.ParticipantMapDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.util.List;

@Configuration
public class ConverterConfig {

    /**
     * 문자열을 ParticipantMapDto 리스트로 변환하는데 사용될 컨버터 빈을 생성
     * @return 문자열을 ParticipantMapDto 리스트로 변환하는 컨버터 빈
     * @author Yujin-nKim(김유진)
     */
    @Bean
    public Converter<String, List<ParticipantMapDto>> stringToParticipantMapDtoConverter() {
        return new StringToParticipantMapDtoConverter();
    }
}