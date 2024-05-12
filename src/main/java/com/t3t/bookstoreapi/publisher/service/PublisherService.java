package com.t3t.bookstoreapi.publisher.service;

import com.t3t.bookstoreapi.model.response.PageResponse;
import com.t3t.bookstoreapi.publisher.exception.PublisherNotFoundException;
import com.t3t.bookstoreapi.publisher.model.request.PublisherCreationRequest;
import com.t3t.bookstoreapi.publisher.model.dto.PublisherDto;
import com.t3t.bookstoreapi.publisher.model.entity.Publisher;
import com.t3t.bookstoreapi.publisher.repository.PublisherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PublisherService {

    private final PublisherRepository publisherRepository;

    @Transactional(readOnly = true)
    public PageResponse<PublisherDto> getPublisherList(Pageable pageable) {

        Page<Publisher> publisherPage = publisherRepository.findAll(pageable);

        List<PublisherDto> publisherDtoList = publisherPage.getContent()
                .stream()
                .map(PublisherDto::of)
                .collect(Collectors.toList());

        return PageResponse.<PublisherDto>builder()
                .content(publisherDtoList)
                .pageNo(publisherPage.getNumber())
                .pageSize(publisherPage.getSize())
                .totalElements(publisherPage.getTotalElements())
                .totalPages(publisherPage.getTotalPages())
                .last(publisherPage.isLast())
                .build();
    }

    @Transactional(readOnly = true)
    public PublisherDto getPublisherById(Long publisherId) {
        return PublisherDto.of(publisherRepository.findById(publisherId)
                .orElseThrow(() -> new PublisherNotFoundException()));
    }

    public PublisherDto createPublisher(Long publisherId, PublisherCreationRequest request) {
        Publisher newPublisher = Publisher.builder()
                .publisherId(publisherId)
                .publisherName(request.getPublisherName())
                .build();

        return PublisherDto.of(publisherRepository.save(newPublisher));
    }

    public PublisherDto updatePublisher(Long publisherId, String publisherName, String publisherEmail) {
        Publisher publisher = publisherRepository.findById(publisherId)
                .orElseThrow(() -> new PublisherNotFoundException(publisherId));

        publisher.setPublisherName(publisherName);
        publisher.setPublisherEmail(publisherEmail);

        return PublisherDto.of(publisher);
    }

    public void deletePublisher(Long publisherId) {
        publisherRepository.delete(publisherRepository.findById(publisherId)
                .orElseThrow(() -> new PublisherNotFoundException(publisherId)));
    }
}
