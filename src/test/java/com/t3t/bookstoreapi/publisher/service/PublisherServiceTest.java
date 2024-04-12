package com.t3t.bookstoreapi.publisher.service;

import com.t3t.bookstoreapi.publisher.model.entity.Publisher;
import com.t3t.bookstoreapi.publisher.repository.PublisherRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
class PublisherServiceTest {

/*    @Autowired
    PublisherRepository publisherRepository;

    @Autowired
    PublisherService publisherService;

    @Test
    void getPublisherById() {
        Publisher publisher1 = publisherRepository.save(Publisher.builder()
                        .publisherId(101L)
                        .publisherName("한빛미디어")
                        .publisherEmail("han@gmail.com")
                        .build());

        Publisher publisher2 = publisherRepository.save(Publisher.builder()
                        .publisherName("비상")
                        .publisherEmail("sang@gmail.com")
                        .build());

        List<Publisher> publisherList = publisherService.getPublisherList();

        assertNotNull(publisherList);
        assertEquals(2, publisherList.size());

        assertEquals(publisher1.getPublisherId(), publisherList.get(0).getPublisherId());
        assertEquals(publisher1.getPublisherName(), publisherList.get(0).getPublisherName());
        assertEquals(publisher1.getPublisherEmail(), publisherList.get(0).getPublisherEmail());

        assertEquals(publisher2.getPublisherId(), publisherList.get(1).getPublisherId());
        assertEquals(publisher2.getPublisherName(), publisherList.get(1).getPublisherName());
        assertEquals(publisher2.getPublisherEmail(), publisherList.get(1).getPublisherEmail());
    }

    @Test
    void createPublisher() {

    }

    @Test
    void updatePublisher() {

    }

    @Test
    void deletePublisher() {

    }*/
}
