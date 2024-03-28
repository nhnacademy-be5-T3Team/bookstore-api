package com.t3t.bookstoreapi.entity;

import com.t3t.bookstoreapi.category.entity.Category;
import com.t3t.bookstoreapi.category.repository.CategoryRepository;
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

    @Test
    @DisplayName("Tag entity 맵핑 테스트")
    void testTagEntityMapping() {
        Tag tag = Tag.builder().
                tagName("TestTagName").
                build();

        tagRepository.save(tag);

        Tag savedTag = tagRepository.findById(tag.getTagId()).orElse(null);

        assert savedTag != null;

        assertEquals("TestTagName", savedTag.getTagName());
    }

    @Test
    @DisplayName("부모 Category entity 맵핑 테스트")
    void testParentCategoryEntityMapping() {
        Category category = Category.builder().categoryName("TestCategoryName").build();

        categoryRepository.save(category);

        Category savedCategory = categoryRepository.findById(category.getCategoryId()).orElse(null);

        assert savedCategory != null;

        assertEquals("TestCategoryName", savedCategory.getCategoryName());
    }

    @Test
    @DisplayName("자식 Category entity 맵핑 테스트")
    void testChildCategoryEntityMapping() {
        Category parentCategory = Category.builder().categoryName("TestParentCategoryName").build();

        categoryRepository.save(parentCategory);

        Category childCategory = Category.builder().
                parentCategoryId(parentCategory.getParentCategoryId())
                .categoryName("TestChildCategoryName")
                .build();


        Category savedCategory = categoryRepository.findById(childCategory.getCategoryId()).orElse(null);

        assert savedCategory != null;

        assertEquals("TestChildCategoryName", savedCategory.getCategoryName());
    }
}
