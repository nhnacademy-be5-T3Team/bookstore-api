package com.t3t.bookstoreapi.tag.service;

import com.t3t.bookstoreapi.tag.model.dto.TagDto;
import com.t3t.bookstoreapi.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class TagService {
    private final TagRepository tagRepository;

    @Transactional(readOnly = true)
    public Page<TagDto> getTags(Pageable pageable) {
        return tagRepository.findAll(pageable).map(
                tag -> TagDto.builder()
                        .tagId(tag.getTagId())
                        .tagName(tag.getTagName())
                        .build());
    }
}
