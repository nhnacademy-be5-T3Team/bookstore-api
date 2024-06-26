package com.t3t.bookstoreapi.tag.controller;

import com.t3t.bookstoreapi.model.response.BaseResponse;
import com.t3t.bookstoreapi.model.response.PageResponse;
import com.t3t.bookstoreapi.tag.model.dto.TagDto;
import com.t3t.bookstoreapi.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;

    /**
     * 태그 목록 조회
     * @param pageNo   태그 목록의 페이지 번호 (기본값: 0).
     * @param pageSize 페이지 당 항목 수 (기본값: 10).
     * @param sortBy   정렬 기준 필드 (기본값: tagId).
     * @return 200 OK, 태그 목록을 포함한 ResponseEntity. <br>
     *         204 NO_CONTENT, 데이터가 없는 경우 메시지 반환
     * @author Yujin-nKim(김유진)
     */
    @GetMapping("/tags")
    public ResponseEntity<BaseResponse<PageResponse<TagDto>>> getTagList(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "tagId", required = false) String sortBy) {

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).ascending());

        PageResponse<TagDto> tagList = tagService.getTagList(pageable);

        if (tagList.getContent().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
                    new BaseResponse<PageResponse<TagDto>>().message("등록된 태그 정보가 없습니다.")
            );
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                new BaseResponse<PageResponse<TagDto>>().data(tagList)
        );
    }

    /**
     * 태그 생성 요청
     * @param tagName 태그 이름
     * @return 200 OK, 메세지
     * @author Yujin-nKim(김유진)
     */
    @PostMapping("/tags")
    public ResponseEntity<BaseResponse<Void>> createTag(@RequestParam String tagName) {
        tagService.createTag(tagName);
        return ResponseEntity.status(HttpStatus.OK).body(
                new BaseResponse<Void>().message("태그 생성 요청이 처리되었습니다."));
    }

    /**
     * 태그 수정 요청
     * @param tagId 수정할 태그의 식별자
     * @param tagName 태그 이름
     * @return 200 OK, 메세지
     * @author Yujin-nKim(김유진)
     */
    @PutMapping("/tags/{tagId}")
    public ResponseEntity<BaseResponse<Void>> modifyTag(@PathVariable Long tagId, @RequestParam String tagName) {
        tagService.modifyTag(tagId, tagName);
        return ResponseEntity.status(HttpStatus.OK).body(
                new BaseResponse<Void>().message("태그 수정 요청이 처리되었습니다."));
    }

    /**
     * 태그 상세  조회
     * @param tagId 수정할 태그의 식별자
     * @return 태그 상세
     * @author Yujin-nKim(김유진)
     */
    @GetMapping("/tags/{tagId}")
    public ResponseEntity<BaseResponse<TagDto>> getTag(@PathVariable Long tagId) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new BaseResponse<TagDto>().data(tagService.getTag(tagId)));
    }
}
