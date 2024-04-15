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

@RestController
@RequiredArgsConstructor
public class PublisherController {
    private final PublisherService publisherService;

    @GetMapping("/publishers")
    public ResponseEntity<BaseResponse<List<PublisherDto>>> getPulisherList() {
        List<PublisherDto> publisherList = publisherService.getPublisherList();

        return publisherList.isEmpty() ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).body(new BaseResponse<List<PublisherDto>>().message("등록된 출판사가 없습니다.")) :
                ResponseEntity.ok(new BaseResponse<List<PublisherDto>>().data(publisherList));
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
