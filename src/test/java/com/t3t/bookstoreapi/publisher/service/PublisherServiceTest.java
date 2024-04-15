package com.t3t.bookstoreapi.publisher.service;

import com.t3t.bookstoreapi.publisher.exception.PublisherNotFoundException;
import com.t3t.bookstoreapi.publisher.model.dto.PublisherDto;
import com.t3t.bookstoreapi.publisher.model.entity.Publisher;
import com.t3t.bookstoreapi.publisher.model.request.PublisherCreationRequest;
import com.t3t.bookstoreapi.publisher.repository.PublisherRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class PublisherServiceTest {

    @Mock
    PublisherRepository publisherRepository;

    @InjectMocks
    PublisherService publisherService;

    @Test
    void getPublisherList() {
        // given
        List<Publisher> testPublisherList = List.of(
                Publisher.builder().publisherId(101L).publisherName("한빛미디어").publisherEmail("han@gmail.com").build(),
                Publisher.builder().publisherId(102L).publisherName("비상").publisherEmail("sang@gmail.com").build(),
                Publisher.builder().publisherId(103L).publisherName("북랩").publisherEmail("book@naver.com").build()
        );

        Mockito.doReturn(testPublisherList).when(publisherRepository).findAll();

        // when
        List<PublisherDto> resultPublisherDtoList = publisherService.getPublisherList();

        // then
        Assertions.assertEquals(testPublisherList.size(), resultPublisherDtoList.size());

        for(int i = 0; i < testPublisherList.size(); i++) {
            Assertions.assertEquals(testPublisherList.get(i).getPublisherId(), resultPublisherDtoList.get(i).getPublisherId());
            Assertions.assertEquals(testPublisherList.get(i).getPublisherName(), resultPublisherDtoList.get(i).getPublisherName());
            Assertions.assertEquals(testPublisherList.get(i).getPublisherEmail(), resultPublisherDtoList.get(i).getPublisherEmail());
        }
    }

    @Test
    void getPublisherById() {
        // given
        Publisher testPublisher = Publisher.builder()
                        .publisherId(101L)
                        .publisherName("한빛미디어")
                        .publisherEmail("han@gmail.com")
                        .build();

        Mockito.doReturn(java.util.Optional.of(testPublisher)).when(publisherRepository).findById(testPublisher.getPublisherId());

        // when
        PublisherDto resultPublisherDto = publisherService.getPublisherById(testPublisher.getPublisherId());

        // then
        Assertions.assertEquals(testPublisher.getPublisherId(), resultPublisherDto.getPublisherId());
        Assertions.assertEquals(testPublisher.getPublisherName(), resultPublisherDto.getPublisherName());
        Assertions.assertEquals(testPublisher.getPublisherEmail(), resultPublisherDto.getPublisherEmail());
    }

    @Test
    void getPublisherById_NotFound() {
        long testPublisherId = 0L;

        // given
        Mockito.doReturn(Optional.empty()).when(publisherRepository).findById(testPublisherId);

        // when & then
        Assertions.assertThrows(PublisherNotFoundException.class, () -> publisherService.getPublisherById(testPublisherId));
    }

    @Test
    void createPublisher() {
        // given
        long testPublisherId = 101L;
        String testPublisherName = "한빛미디어";
        String testPublisherEmail = "han@gmail.com";

        Publisher testPublisher = Publisher.builder()
                .publisherId(testPublisherId)
                .publisherName(testPublisherName)
                .publisherEmail(testPublisherEmail)
                .build();

        PublisherCreationRequest testRequest = PublisherCreationRequest.builder()
                .publisherId(testPublisherId)
                .publisherName(testPublisherName)
                .build();

        Mockito.when(publisherRepository.save(Mockito.any(Publisher.class))).thenReturn(testPublisher);

        // when
        PublisherDto resultPublisherDto = publisherService.createPublisher(testPublisherId, testRequest);

        // then
        Assertions.assertEquals(testPublisher.getPublisherId(), resultPublisherDto.getPublisherId());
        Assertions.assertEquals(testPublisher.getPublisherName(), resultPublisherDto.getPublisherName());
        Assertions.assertEquals(testPublisher.getPublisherEmail(), resultPublisherDto.getPublisherEmail());
    }

    @Test
    void updatePublisher() {
        // given
        Publisher testPublisher = Publisher.builder()
                .publisherId(101L)
                .publisherName("한빛미디어")
                .publisherEmail("han@gmail.com")
                .build();

        Mockito.doReturn(java.util.Optional.of(testPublisher)).when(publisherRepository).findById(testPublisher.getPublisherId());

        // when
        String resultPublisherDtoName = "비상";
        String resultPublisherDtoEmail = "sang@gamil.com";

        PublisherDto resultPublisherDto = publisherService.updatePublisher(testPublisher.getPublisherId(), resultPublisherDtoName, resultPublisherDtoEmail);

        // then
        Assertions.assertEquals(testPublisher.getPublisherName(), resultPublisherDtoName);
        Assertions.assertEquals(testPublisher.getPublisherEmail(), resultPublisherDtoEmail);
    }

    @Test
    void deletePublisher() {
        // given
        Publisher testPublisher = Publisher.builder()
                .publisherId(101L)
                .publisherName("한빛미디어")
                .publisherEmail("han@gmail.com")
                .build();

        Mockito.doReturn(java.util.Optional.of(testPublisher)).when(publisherRepository).findById(testPublisher.getPublisherId());

        // when
        publisherService.deletePublisher(testPublisher.getPublisherId());

        // then
        Mockito.verify(publisherRepository, Mockito.times(1)).delete(testPublisher);
    }

    @Test
    void deletePublisher_PublisherNotFoundException() {
        // given
        long testPublisherId = 101L;
        Mockito.when(publisherRepository.findById(testPublisherId)).thenReturn(Optional.empty());

        // when & then
        Assertions.assertThrows(PublisherNotFoundException.class,
                () -> publisherService.deletePublisher(testPublisherId));
    }
}
