package com.t3t.bookstoreapi.publisher.model.dto;

import com.t3t.bookstoreapi.publisher.model.entity.Publisher;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Data
@Getter
@Builder
public class PublisherDto {
    @NotNull
    private Long publisherId;
    private String publisherName;
    private String publisherEmail;

    public static PublisherDto of(Publisher publisher) {
        return PublisherDto.builder()
                .publisherId(publisher.getPublisherId())
                .publisherName(publisher.getPublisherName())
                .publisherEmail(publisher.getPublisherEmail())
                .build();
     }
}
