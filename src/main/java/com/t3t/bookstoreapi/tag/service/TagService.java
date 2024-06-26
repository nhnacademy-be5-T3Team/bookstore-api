package com.t3t.bookstoreapi.tag.service;

import com.t3t.bookstoreapi.model.response.PageResponse;
import com.t3t.bookstoreapi.tag.exception.TagNameAlreadyExistsException;
import com.t3t.bookstoreapi.tag.exception.TagNotFoundException;
import com.t3t.bookstoreapi.tag.model.dto.TagDto;
import com.t3t.bookstoreapi.tag.model.entity.Tag;
import com.t3t.bookstoreapi.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    /**
     * 태그 목록 조회
     * @param pageable 페이지 정보
     * @return 페이지 응답 객체 (PageResponse)
     * @author Yujin-nKim(김유진)
     */
    @Transactional(readOnly = true)
    public PageResponse<TagDto> getTagList(Pageable pageable) {
        Page<Tag> tagPage = tagRepository.findAll(pageable);

        List<TagDto> tagDtoList = tagPage.getContent()
                .stream()
                .map(TagDto::of)
                .collect(Collectors.toList());

        return PageResponse.<TagDto>builder()
                .content(tagDtoList)
                .pageNo(tagPage.getNumber())
                .pageSize(tagPage.getSize())
                .totalElements(tagPage.getTotalElements())
                .totalPages(tagPage.getTotalPages())
                .last(tagPage.isLast())
                .build();
    }

    /**
     * 태그 생성
     * @param tagName 태그 이름
     * @author Yujin-nKim(김유진)
     */
    public void createTag(String tagName) {
        if (tagRepository.existsByTagName(tagName)) {
            throw new TagNameAlreadyExistsException();
        }
        tagRepository.save(Tag.builder().tagName(tagName).build());
    }

    /**
     * 태그 수정
     * @param tagId 수정할 태그의 식별자
     * @param tagName 태그 이름
     * @author Yujin-nKim(김유진)
     */
    public void modifyTag(Long tagId, String tagName) {
        Tag tag = tagRepository.findById(tagId).orElseThrow(TagNotFoundException::new);
        tag.updateTagName(tagName);
    }

    /**
     * 태그 상세  조회
     * @param tagId 수정할 태그의 식별자
     * @return 태그 상세
     * @author Yujin-nKim(김유진)
     */
    public TagDto getTag(Long tagId) {
        return TagDto.of(tagRepository.findById(tagId).orElseThrow(TagNotFoundException::new));
    }
}