package com.t3t.bookstoreapi.publisher.service;

import com.t3t.bookstoreapi.publisher.exception.PublisherNotFoundException;
import com.t3t.bookstoreapi.publisher.model.request.PublisherCreationRequest;
import com.t3t.bookstoreapi.publisher.model.dto.PublisherDto;
import com.t3t.bookstoreapi.publisher.model.entity.Publisher;
import com.t3t.bookstoreapi.publisher.repository.PublisherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 출판사 서비스를 제공하는 클래스
 * 출판사 생성, 조회, 수정, 삭제 API 제공
 */
@Service
@Transactional
@RequiredArgsConstructor
public class PublisherService {

    private final PublisherRepository publisherRepository;

    /**
     * 모든 출판사의 목록 조회
     * @return 출판사 정보가 담긴 {@link PublisherDto}의 리스트를 반환
     *
     * @author hydrationn(박수화)
     */
    @Transactional(readOnly = true)
    public List<PublisherDto> getPublisherList() {
        return publisherRepository.findAll().stream().map(PublisherDto::of).collect(Collectors.toList());
    }

    /**
     * 특정 출판사의 정보 조회
     * @param publisherId 조회할 출판사의 ID
     * @return 해당 출판사의 정보가 담긴 {@link PublisherDto} 반환
     * @throws PublisherNotFoundException 주어진 ID의 출판사를 찾을 수 없을 때 발생
     *
     * @author hydrationn(박수화)
     */
    @Transactional(readOnly = true)
    public PublisherDto getPublisherById(Long publisherId) {
        return PublisherDto.of(publisherRepository.findById(publisherId)
                .orElseThrow(() -> new PublisherNotFoundException()));
    }

    /**
     * 새로운 출판사 생성
     * @param publisherId 생성할 출판사 ID
     * @param request 출판사 생성을 위한 요청 정보가 담긴 {@link PublisherCreationRequest} 객체
     * @return 생성된 출판사의 정보가 담긴 {@link PublisherDto}를 반환
     *
     * @author hydrationn(박수화)
     */
    public PublisherDto createPublisher(Long publisherId, PublisherCreationRequest request) {
        Publisher newPublisher = Publisher.builder()
                .publisherId(publisherId)
                .publisherName(request.getPublisherName())
                .build();

        return PublisherDto.of(publisherRepository.save(newPublisher));
    }

    /**
     * 주어진 ID의 출판사 정보를 업데이트
     * @param publisherId 업데이트할 출판사의 ID
     * @param publisherName 새로운 출판사 이름
     * @param publisherEmail 새로운 출판사 이메일
     * @return 업데이트된 출판사의 정보가 담긴 {@link PublisherDto} 반환
     * @throws PublisherNotFoundException 주어진 ID의 출판사를 찾을 수 없을 때 발생
     *
     * @author hydrationn(박수화)
     */
    public PublisherDto updatePublisher(Long publisherId, String publisherName, String publisherEmail) {
        Publisher publisher = publisherRepository.findById(publisherId)
                .orElseThrow(() -> new PublisherNotFoundException(publisherId));

        publisher.setPublisherName(publisherName);
        publisher.setPublisherEmail(publisherEmail);

        return PublisherDto.of(publisher);
    }

    /**
     * 특정 출판사 삭제
     * @param publisherId 삭제할 출판사의 ID
     * @throws PublisherNotFoundException 특정 출판사를 찾을 수 없을 때 발생
     *
     * @author hydrationn(박수화)
     */
    public void deletePublisher(Long publisherId) {
        publisherRepository.delete(publisherRepository.findById(publisherId)
                .orElseThrow(() -> new PublisherNotFoundException(publisherId)));
    }
}
