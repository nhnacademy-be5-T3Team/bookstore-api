package com.t3t.bookstoreapi.tag.controller;

import com.t3t.bookstoreapi.model.response.BaseResponse;
import com.t3t.bookstoreapi.tag.model.dto.TagDto;
import com.t3t.bookstoreapi.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TagController {
    private final TagService tagService;

    @GetMapping("/tags")
    public ResponseEntity<BaseResponse<Page<TagDto>>> getTags(@PageableDefault(size = 10, sort = "tagId", direction = Sort.Direction.DESC) Pageable pageable) {
        if (tagService.getTags(pageable).isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(new BaseResponse<Page<TagDto>>()
                .data(tagService.getTags(pageable)));
    }
}
