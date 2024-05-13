package com.t3t.bookstoreapi.publisher.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PublisherCreationRequest {
    @NotNull
    private Long publisherId;

    @NotNull
    private String publisherName;
}
