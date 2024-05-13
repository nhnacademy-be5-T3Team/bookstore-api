package com.t3t.bookstoreapi.publisher.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 출판사 상세 정보 생성을 위한 요청 데이터 모델
 * 관리자가 출판사 상세 정보를 생성할 때 필요한 정보를 전달하는 데 사용
 *
 * @author hydrationn(박수화)
 */
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
