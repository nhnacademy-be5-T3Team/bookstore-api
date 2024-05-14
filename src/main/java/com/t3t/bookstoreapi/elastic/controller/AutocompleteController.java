package com.t3t.bookstoreapi.elastic.controller;

import com.t3t.bookstoreapi.elastic.service.AutocompleteService;
import com.t3t.bookstoreapi.model.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AutocompleteController {
    private final AutocompleteService autocompleteService;
    /**
     * elasticsearch 기반 실시간 자동완성
     *
     * @param prefix      text 검색어
     * @return HTTP 상태 및 도서 목록 응답
     * @author parkjonggyeong18(박종경)
     */
    @GetMapping("/autocomplete")
    public ResponseEntity<BaseResponse<List<String>>> autocomplete(@RequestParam String prefix) {
        BaseResponse<List<String>> autocomplete;
        try {
            autocomplete = autocompleteService.autocomplete(prefix);
            return ResponseEntity.ok().body(autocomplete);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BaseResponse<List<String>>().message("잘못된 접근입니다."));
        }
    }
}
