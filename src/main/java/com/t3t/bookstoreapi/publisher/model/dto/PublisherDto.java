package com.t3t.bookstoreapi.publisher.model.dto;

import com.t3t.bookstoreapi.publisher.model.entity.Publisher;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PublisherDto {
    private Long publisherId;
    private String publisherName;

    public static PublisherDto of(Publisher publisher) {
        return PublisherDto.builder()
                .publisherId(publisher.getPublisherId())
                .publisherName(builder().publisherName)
                .build();
    }
}
