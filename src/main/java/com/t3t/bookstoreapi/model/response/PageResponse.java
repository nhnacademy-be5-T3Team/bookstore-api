package com.t3t.bookstoreapi.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageResponse<T> {
    private List<T> content; // 현재 페이지에 해당하는 데이터들
    private int pageNo; // 현재 페이지 넘버
    private int pageSize; // 페이지 당 데이터 수
    private long totalElements; // 데이터 총 개수
    private int totalPages; // 총 페이지 수
    private boolean last; // 현재 페이지가 마지막 페이지인지 여부
}