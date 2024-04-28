package com.t3t.bookstoreapi.publisher.controller;

import com.t3t.bookstoreapi.model.response.BaseResponse;
import com.t3t.bookstoreapi.model.response.PageResponse;
import com.t3t.bookstoreapi.publisher.model.request.PublisherCreationRequest;
import com.t3t.bookstoreapi.publisher.model.dto.PublisherDto;
import com.t3t.bookstoreapi.publisher.service.PublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class PublisherController {
    private final PublisherService publisherService;

    @GetMapping("/publishers")
    public ResponseEntity<BaseResponse<PageResponse<PublisherDto>>> getPulisherList(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "publisherId", required = false) String sortBy) {

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());

        PageResponse<PublisherDto> publisherList = publisherService.getPublisherList(pageable);

        return publisherList.getContent().isEmpty() ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).body(new BaseResponse<PageResponse<PublisherDto>>().message("등록된 출판사가 없습니다.")) :
                ResponseEntity.ok(new BaseResponse<PageResponse<PublisherDto>>().data(publisherList));
    }

    @GetMapping("/publishers/{publisherId}")
    public ResponseEntity<BaseResponse<PublisherDto>> getPublisherById(@PathVariable Long publisherId) {
        return ResponseEntity.ok(new BaseResponse<PublisherDto>()
                .data(publisherService.getPublisherById(publisherId)));
    }

    @PostMapping("/publishers/{publisherId}")
    public ResponseEntity<BaseResponse<PublisherDto>> createPublisher(@PathVariable("publisherId") Long publisherId,
                                                                      @Valid @RequestBody PublisherCreationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping(value = "/publishers/{publisherId}")
    public ResponseEntity<BaseResponse<PublisherDto>> updatePublisher(@PathVariable Long publisherId, @RequestParam String publisherName, @RequestParam String publisherEmail) {
        return ResponseEntity.ok(new BaseResponse<PublisherDto>()
                .data(publisherService.updatePublisher(publisherId, publisherName, publisherEmail)));
    }

    @DeleteMapping("/publishers/{publisherId}")
    public ResponseEntity<BaseResponse<Void>> deletePublisher(@PathVariable("publishers") Long publisherId) {
        publisherService.deletePublisher(publisherId);
        return ResponseEntity.ok(new BaseResponse<Void>());
    }
}
