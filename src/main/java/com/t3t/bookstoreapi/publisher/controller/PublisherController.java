package com.t3t.bookstoreapi.publisher.controller;

import com.t3t.bookstoreapi.model.response.BaseResponse;
import com.t3t.bookstoreapi.publisher.model.dto.PublisherDto;
import com.t3t.bookstoreapi.publisher.model.entity.Publisher;
import com.t3t.bookstoreapi.publisher.service.PublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/publishers")
public class PublisherController {
    private final PublisherService publisherService;

    @GetMapping
    public ResponseEntity<BaseResponse<List<PublisherDto>>> getAllPublishers() {
        List<PublisherDto> publisherDtoList = publisherService.getAllPublishers();

        BaseResponse<List<PublisherDto>> responseBody = new BaseResponse<>();

        return publisherDtoList.isEmpty() ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).body(responseBody.message("등록된 출판사가 없습니다.")) :
                ResponseEntity.ok(responseBody.data(publisherDtoList));
    }

/*    @GetMapping("/{publisherId}")
    public ResponseEntity<BaseResponse<PublisherDto>> getPublisherById(@PathVariable Long publisherId) {
        return ResponseEntity.ok(new BaseResponse<PublisherDto>()
                .data(publisherService.getPublisherById(publisherId)));
    }*/

    @PostMapping
    public Publisher createPublisher(@RequestBody Publisher publisher) {
        return publisherService.createPublisher(publisher);
    }

    @DeleteMapping("/{publisherId}")
    public ResponseEntity<Void> deletePublisher(@PathVariable Long publisherId) {
        publisherService.deletePublisher(publisherId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{publisherId}")
    public ResponseEntity<Publisher> updatePublisher(@PathVariable Long publisherId) {
        Publisher updatedPublisher = publisherService.updatePublisher(publisherId);
        return ResponseEntity.ok(updatedPublisher);
    }
}
