package com.t3t.bookstoreapi.elastic.controller;

import com.t3t.bookstoreapi.elastic.model.response.ElasticResponse;
import com.t3t.bookstoreapi.elastic.service.ElasticCategoryService;
import com.t3t.bookstoreapi.elastic.service.ElasticService;
import com.t3t.bookstoreapi.model.response.BaseResponse;
import com.t3t.bookstoreapi.model.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class ElasticController {

    private final ElasticService elasticService;
    private final ElasticCategoryService elasticCategoryService;

    /**
     * elasticsearch 기반 text 검색
     *
     * @param query      text 검색어
     * @param searchType 검색 유형
     * @param pageNo     페이지 번호 (기본값: 0)
     * @param pageSize   페이지 크기 (기본값: 10)
     * @param sortBy     정렬 기준 (기본값: "_socre")
     * @return HTTP 상태 및 도서 목록 응답
     * @author parkjonggyeong18(박종경)
     */

    @GetMapping("/search")
    public ResponseEntity<BaseResponse<PageResponse<ElasticResponse>>> getSearchPage(
            @RequestParam(value = "query") String query,
            @RequestParam(value = "searchType") String searchType,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "_score", required = false) String sortBy) {
        if (query == null) {
            return ResponseEntity.badRequest().body(new BaseResponse<>());
        }

        Pageable pageable;
        if(Objects.equals(sortBy, "discounted_price")){
            pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        }else{
            pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        }
        PageResponse<ElasticResponse> searchList = elasticService.search(query, searchType, pageable);

        BaseResponse<PageResponse<ElasticResponse>> responseBody = new BaseResponse<>();
        return searchList.getContent().isEmpty()
                ? ResponseEntity.status(HttpStatus.NO_CONTENT).body(responseBody.message("검색한 도서가 없습니다."))
                : ResponseEntity.ok(responseBody.data(searchList));
    }

    /**
     * elasticsearch 기반 category 검색
     *
     * @param query      text 검색어
     * @param searchType 검색 유형
     * @param pageNo     페이지 번호 (기본값: 0)
     * @param pageSize   페이지 크기 (기본값: 10)
     * @param categoryId  카테고리 검색을 위한 카테고리번호
     * @param sortBy     정렬 기준 (기본값: "_socre")
     * @return HTTP 상태 및 도서 목록 응답
     * @author parkjonggyeong18(박종경)
     */
    @GetMapping("/category/{categoryId}/search")
    public ResponseEntity<BaseResponse<PageResponse<ElasticResponse>>> getCategorySearchPage(
            @RequestParam(value = "query") String query,
            @RequestParam(value = "searchType") String searchType,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @PathVariable(value = "categoryId", required = false) BigDecimal categoryId,
            @RequestParam(value = "sortBy", defaultValue = "_score", required = false) String sortBy) {
        if (query == null) {
            return ResponseEntity.badRequest().body(new BaseResponse<>());
        }

        Pageable pageable;
        if(Objects.equals(sortBy, "discounted_price")){
            pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        }else{
            pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        }

        PageResponse<ElasticResponse> searchList =
                elasticCategoryService.search(query, searchType, categoryId, pageable);

        BaseResponse<PageResponse<ElasticResponse>> responseBody = new BaseResponse<>();
        return searchList.getContent().isEmpty()
                ? ResponseEntity.status(HttpStatus.NO_CONTENT).body(responseBody.message("검색한 도서가 없습니다."))
                : ResponseEntity.ok(responseBody.data(searchList));
    }
}


