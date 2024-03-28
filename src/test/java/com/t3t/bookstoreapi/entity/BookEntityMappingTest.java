package com.t3t.bookstoreapi.entity;

import com.t3t.bookstoreapi.publisher.entity.Publisher;
import com.t3t.bookstoreapi.publisher.repository.PublisherRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@ActiveProfiles("prod")
public class BookEntityMappingTest {
    @Autowired
    private PublisherRepository publisherRepository;

    @Test
    @DisplayName("Publisher entity 맵핑 테스트")
    void testPublisherEntityMapping() {
        Publisher publisher = Publisher.builder().
                publisherName("TestPublisher").
                publisherEmail("TestPublisher@example.com").
                build();

        publisherRepository.save(publisher);

        Publisher savedPublisher = publisherRepository.findById(publisher.getPublisherId()).orElse(null);

        assert savedPublisher != null;

        assertEquals("TestPublisher", savedPublisher.getPublisherName());
        assertEquals("TestPublisher@example.com", savedPublisher.getPublisherEmail());
    }
}
