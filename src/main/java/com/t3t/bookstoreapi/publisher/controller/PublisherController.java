package com.t3t.bookstoreapi.publisher.controller;

import com.t3t.bookstoreapi.model.response.BaseResponse;
import com.t3t.bookstoreapi.publisher.model.request.PublisherCreationRequest;
import com.t3t.bookstoreapi.publisher.model.dto.PublisherDto;
import com.t3t.bookstoreapi.publisher.model.entity.Publisher;
import com.t3t.bookstoreapi.publisher.service.PublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 출판사 상세 내역을 관리하는 controller
 * 출판사 생성, 조회, 수정, 삭제 API 제공
 */
@RestController
@RequiredArgsConstructor
public class PublisherController {
    private final PublisherService publisherService;

    /**
     * 모든 출판사 목록을 조회
     * @return 등록된 모든 출판사 리스트를 반환
     *         출판사가 없을 경우 204 No Content 상태와 메시지를 반환
     *
     * @author hydrationn(박수화)
     */
    @GetMapping("/publishers")
    public ResponseEntity<BaseResponse<List<PublisherDto>>> getPulisherList() {
        List<PublisherDto> publisherList = publisherService.getPublisherList();

        return publisherList.isEmpty() ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).body(new BaseResponse<List<PublisherDto>>().message("등록된 출판사가 없습니다.")) :
                ResponseEntity.ok(new BaseResponse<List<PublisherDto>>().data(publisherList));
    }

    /**
     * 특정 출판사의 상세 정보 조회
     * @param publisherId 조회할 출판사 ID
     * @return 조회된 출판사의 상세 정보를 반환
     *
     * @author hydrationn(박수화)
     */
    @GetMapping("/publishers/{publisherId}")
    public ResponseEntity<BaseResponse<PublisherDto>> getPublisherById(@PathVariable Long publisherId) {
        return ResponseEntity.ok(new BaseResponse<PublisherDto>()
                .data(publisherService.getPublisherById(publisherId)));
    }

    /**
     * 새로운 출판사 생성
     * @param publisherId 생성할 출판사의 ID
     * @param request 출판사 생성 요청 정보
     * @return 생성된 출판사의 정보를 반환
     *
     * @author hydrationn(박수화)
     */
    @PostMapping("/publishers/{publisherId}")
    public ResponseEntity<BaseResponse<PublisherDto>> createPublisher(@PathVariable("publisherId") Long publisherId,
                                                                      @Valid @RequestBody PublisherCreationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 특정 출판사의 정보 업데이트
     * @param publisherId 업데이트할 출판사의 ID
     * @param publisherName 수정될 출판사 이름
     * @param publisherEmail 수정될 출판사 이메일
     *
     * @return 업데이트된 출판사 정보 반환
     */
    @PutMapping(value = "/publishers/{publisherId}")
    public ResponseEntity<BaseResponse<PublisherDto>> updatePublisher(@PathVariable Long publisherId, @RequestParam String publisherName, @RequestParam String publisherEmail) {
        return ResponseEntity.ok(new BaseResponse<PublisherDto>()
                .data(publisherService.updatePublisher(publisherId, publisherName, publisherEmail)));
    }

    /**
     * 특정 출판사 삭제
     * @param publisherId 삭제할 출판사의 ID
     * @return 삭제 작업이 성공했음을 나타내는 ResponseEntity 반환
     *
     * @author hydrationn(박수화)
     */
    @DeleteMapping("/publishers/{publisherId}")
    public ResponseEntity<BaseResponse<Void>> deletePublisher(@PathVariable("publishers") Long publisherId) {
        publisherService.deletePublisher(publisherId);
        return ResponseEntity.ok(new BaseResponse<Void>());
    }
}
