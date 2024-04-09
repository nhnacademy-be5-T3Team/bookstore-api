package com.t3t.bookstoreapi.publisher.service;

import com.t3t.bookstoreapi.publisher.PublisherNotFoundException;
import com.t3t.bookstoreapi.publisher.model.dto.PublisherDto;
import com.t3t.bookstoreapi.publisher.model.entity.Publisher;
import com.t3t.bookstoreapi.publisher.repository.PublisherRepository;
import lombok.RequiredArgsConstructor;
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
    public List<PublisherDto> getAllPublishers() {
        return publisherRepository.findAll().stream()
                .map(PublisherDto::of)
                .collect(Collectors.toList());
    }

/*    @Transactional(readOnly = true)
    public PublisherDto getPublisherById(Long publisherId) {
        return PublisherDto.of(publisherRepository.findById(publisherId))
                .orElseThrow(() -> new PublisherNotFoundException());
    }*/

    public Publisher createPublisher(Publisher publisher) {
        return publisherRepository.save(publisher);
    }

    public void deletePublisher(Long id) {
        publisherRepository.deleteById(id);
    }

    public Publisher updatePublisher(Long publisherId) {
        Publisher publisher = publisherRepository.findById(publisherId)
                .orElseThrow(() -> new PublisherNotFoundException());

        Publisher updatedPublisher = Publisher.builder()
                .publisherId(publisher.getPublisherId())
                .publisherName(publisher.getPublisherName())
                .publisherEmail(publisher.getPublisherEmail())
                .build();

        return publisherRepository.save(updatedPublisher);
    }
}
