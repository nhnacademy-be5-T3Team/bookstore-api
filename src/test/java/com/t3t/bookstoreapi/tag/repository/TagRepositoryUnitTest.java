package com.t3t.bookstoreapi.tag.repository;

import com.t3t.bookstoreapi.tag.model.entity.Tag;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class TagRepositoryUnitTest {
    @Autowired
    private TagRepository tagRepository;

    @Test
    @DisplayName("모든 카테고리 조회 테스트")
    void testFindAll() {
        Tag tag = tagRepository.save(
                Tag.builder()
                        .tagId(1L)
                        .tagName("tag")
                        .build()
        );
    }
}
