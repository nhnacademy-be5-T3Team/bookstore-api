package com.t3t.bookstoreapi.tag.service;

import com.t3t.bookstoreapi.model.response.PageResponse;
import com.t3t.bookstoreapi.tag.model.dto.TagDto;
import com.t3t.bookstoreapi.tag.model.entity.Tag;
import com.t3t.bookstoreapi.tag.repository.TagRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * {@link TagService} 클래스의 단위 테스트
 *
 * @author Yujin-nKim(김유진)
 */
@ExtendWith(MockitoExtension.class)
class TagServiceUnitTest {
    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TagService tagService;

    @Order(1)
    @DisplayName("태그 목록 조회 테스트")
    @Test
    void getTagList() {

        // dummy data setting
        List<Tag> tagList = new ArrayList<>();
        for(int i = 1; i <= 20; i++) {
            tagList.add(Tag.builder()
                    .tagId((long)i)
                    .tagName("TestTagName" + i)
                    .build());
        }

        Pageable pageable = PageRequest.of(0, 10, Sort.by("tagId").ascending());

        Page<Tag> tagPage = new PageImpl<>(tagList, pageable, tagList.size());
        when(tagRepository.findAll(pageable)).thenReturn(tagPage);

        PageResponse<TagDto> result = tagService.getTagList(pageable);

        assertNotNull(result);
        assertEquals(tagList.size(), result.getContent().size());
        assertEquals(0, result.getPageNo());
        assertEquals(10, result.getPageSize());
        assertEquals(2, result.getTotalPages());
        assertEquals(tagList.size(), result.getTotalElements());
    }
}