package com.t3t.bookstoreapi.entity;

import com.t3t.bookstoreapi.category.entity.Category;
import com.t3t.bookstoreapi.category.repository.CategoryRepository;
import com.t3t.bookstoreapi.participant.entity.Participant;
import com.t3t.bookstoreapi.participant.entity.ParticipantRole;
import com.t3t.bookstoreapi.participant.repository.ParticipantRepository;
import com.t3t.bookstoreapi.participant.repository.ParticipantRoleRepository;
import com.t3t.bookstoreapi.publisher.entity.Publisher;
import com.t3t.bookstoreapi.publisher.repository.PublisherRepository;
import com.t3t.bookstoreapi.tag.entity.Tag;
import com.t3t.bookstoreapi.tag.repository.TagRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
@ActiveProfiles("prod")
public class BookEntityMappingTest {
    @Autowired
    private PublisherRepository publisherRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ParticipantRepository participantRepository;
    @Autowired
    private ParticipantRoleRepository participantRoleRepository;

    @Test
    @DisplayName("Publisher entity 맵핑 테스트")
    void testPublisherEntityMapping() {

        String publisherName = "TestPublisher";
        String publisherEmail = "TestPublisher@example.com";

        Publisher publisher = Publisher.builder().
                publisherName(publisherName).
                publisherEmail(publisherEmail).
                build();

        publisherRepository.save(publisher);

        Publisher savedPublisher = publisherRepository.findById(publisher.getPublisherId()).orElse(null);

        assertNotNull(savedPublisher);
        assertEquals(publisherName, savedPublisher.getPublisherName());
        assertEquals(publisherEmail, savedPublisher.getPublisherEmail());
    }

    @Test
    @DisplayName("Tag entity 맵핑 테스트")
    void testTagEntityMapping() {

        String tagName = "TestTagName";

        Tag tag = Tag.builder().
                tagName(tagName).
                build();

        tagRepository.save(tag);

        Tag savedTag = tagRepository.findById(tag.getTagId()).orElse(null);

        assertNotNull(savedTag);
        assertEquals(tagName, savedTag.getTagName());
    }

    @Test
    @DisplayName("부모 Category entity 맵핑 테스트")
    void testParentCategoryEntityMapping() {

        String categoryName = "TestCategoryName";

        Category category = Category.builder().categoryName(categoryName).build();

        categoryRepository.save(category);

        Category savedCategory = categoryRepository.findById(category.getCategoryId()).orElse(null);

        assertNotNull(savedCategory);
        assertEquals(categoryName, savedCategory.getCategoryName());
    }

    @Test
    @DisplayName("자식 Category entity 맵핑 테스트")
    void testChildCategoryEntityMapping() {

        String categoryName = "TestChildCategoryName";

        Category parentCategory = Category.builder().categoryName("TestParentCategoryName").build();

        categoryRepository.save(parentCategory);

        Category childCategory = Category.builder().
                parentCategoryId(parentCategory.getParentCategoryId())
                .categoryName(categoryName)
                .build();

        categoryRepository.save(childCategory);

        Category savedCategory = categoryRepository.findById(childCategory.getCategoryId()).orElse(null);

        assertNotNull(savedCategory);
        assertEquals(categoryName, savedCategory.getCategoryName());
    }

    @Test
    @DisplayName("Participant entity 맵핑 테스트")
    void testParticipantEntityMapping() {

        String participantName = "TestParticipantName";
        String participantEmail = "TestParticipantEmail@example.com";

        Participant participant = Participant.builder().
                participantName(participantName).
                participantEmail(participantEmail).build();

        participantRepository.save(participant);

        Participant savedParticipant = participantRepository.findById(participant.getParticipantId()).orElse(null);

        assertNotNull(savedParticipant);
        assertEquals(participantName, savedParticipant.getParticipantName());
        assertEquals(participantEmail, savedParticipant.getParticipantEmail());
    }

    @Test
    @DisplayName("ParticipantRole entity 맵핑 테스트")
    void testParticipantRoleEntityMapping() {

        String participantRoleName = "TestParticipantRoleName";

        ParticipantRole participantRole = ParticipantRole.builder().participantRoleName(participantRoleName).build();

        participantRoleRepository.save(participantRole);

        ParticipantRole savedParticipantRole = participantRoleRepository.findById(participantRole.getParticipantRoleId()).orElse(null);

        assertNotNull(savedParticipantRole);
        assertEquals(participantRoleName, savedParticipantRole.getParticipantRoleName());
    }
}
