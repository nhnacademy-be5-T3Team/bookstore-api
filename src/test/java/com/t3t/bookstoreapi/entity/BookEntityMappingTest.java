package com.t3t.bookstoreapi.entity;

import com.t3t.bookstoreapi.book.model.entity.Book;
import com.t3t.bookstoreapi.book.repository.BookRepository;
import com.t3t.bookstoreapi.category.model.entity.Category;
import com.t3t.bookstoreapi.category.repository.CategoryRepository;
import com.t3t.bookstoreapi.participant.model.entity.Participant;
import com.t3t.bookstoreapi.participant.model.entity.ParticipantRole;
import com.t3t.bookstoreapi.participant.repository.ParticipantRepository;
import com.t3t.bookstoreapi.participant.repository.ParticipantRoleRepository;
import com.t3t.bookstoreapi.publisher.model.entity.Publisher;
import com.t3t.bookstoreapi.publisher.repository.PublisherRepository;
import com.t3t.bookstoreapi.tag.model.entity.Tag;
import com.t3t.bookstoreapi.tag.repository.TagRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
@ActiveProfiles("prod")
class BookEntityMappingTest {
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
    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("Publisher entity 맵핑 테스트")
    void testPublisherEntityMapping() {

        String publisherName = "TestPublisher";
        String publisherEmail = "TestPublisher@example.com";

        Publisher publisher = Publisher.builder()
                .publisherName(publisherName)
                .publisherEmail(publisherEmail)
                .build();

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

        Tag tag = Tag.builder()
                .tagName(tagName)
                .build();

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

        Category childCategory = Category.builder()
                .parentCategoryId(parentCategory.getParentCategoryId())
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

        Participant participant = Participant.builder()
                .participantName(participantName)
                .participantEmail(participantEmail)
                .build();

        participantRepository.save(participant);

        Participant savedParticipant = participantRepository.findById(participant.getParticipantId()).orElse(null);

        assertNotNull(savedParticipant);
        assertEquals(participantName, savedParticipant.getParticipantName());
        assertEquals(participantEmail, savedParticipant.getParticipantEmail());
    }

    @Test
    @DisplayName("ParticipantRole entity 맵핑 테스트")
    void testParticipantRoleEntityMapping() {

        String participantRoleNameEn = "TestParticipantRoleNameEn";
        String participantRoleNameKr = "TestParticipantRoleNameKr";

        ParticipantRole participantRole = ParticipantRole.builder().participantRoleNameEn(participantRoleNameEn).participantRoleNameKr(participantRoleNameKr).build();

        participantRoleRepository.save(participantRole);

        ParticipantRole savedParticipantRole = participantRoleRepository.findById(participantRole.getParticipantRoleId()).orElse(null);

        assertNotNull(savedParticipantRole);
        assertEquals(participantRoleNameEn, savedParticipantRole.getParticipantRoleNameEn());

    }

    @Test
    @DisplayName("Book entity 맵핑 테스트")
    void testBookEntityMapping() {
        Publisher publisher = Publisher.builder()
                .publisherName("TestPublisher")
                .publisherEmail("TestPublisher@example.com")
                .build();

        publisherRepository.save(publisher);

        Book book = Book.builder()
                .publisher(publisher)
                .bookName("어린왕자")
                .bookIndex("예시 목차")
                .bookDesc("어린왕자는 황소와의 대화, 별의 왕과의 대화, 장미꽃과의 대화 등을 통해 인생의 진리를 탐구하는 내용이 담겨있다.")
                .bookIsbn("9788966863307")
                .bookPrice(new BigDecimal("19.99"))
                .bookDiscount(new BigDecimal("0.1"))
                .bookPackage(1)
                .bookPublished(LocalDate.of(1943, Month.APRIL, 6))
                .bookStock(100)
                .bookAverageScore(4.5f)
                .bookLikeCount(500)
                .build();

        bookRepository.save(book);

        Book savedBook = bookRepository.findById(book.getBookId()).orElse(null);

        assertNotNull(savedBook);
        assertEquals("9788966863307", savedBook.getBookIsbn());

    }
}
