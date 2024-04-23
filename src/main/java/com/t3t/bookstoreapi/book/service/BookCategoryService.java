package com.t3t.bookstoreapi.book.service;

import com.t3t.bookstoreapi.book.model.dto.ParticipantRoleRegistrationDto;
import com.t3t.bookstoreapi.book.model.dto.ParticipantRoleRegistrationDtoByBookId;
import com.t3t.bookstoreapi.book.model.response.BookDetailResponse;
import com.t3t.bookstoreapi.book.repository.BookCategoryRepository;
import com.t3t.bookstoreapi.book.repository.BookRepository;
import com.t3t.bookstoreapi.category.exception.CategoryNotFoundException;
import com.t3t.bookstoreapi.category.model.entity.Category;
import com.t3t.bookstoreapi.category.repository.CategoryRepository;
import com.t3t.bookstoreapi.model.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Transactional
@Service
public class BookCategoryService {
    private final CategoryRepository categoryRepository;
    private final BookCategoryRepository bookCategoryRepository;
    private final BookRepository bookRepository;


    /**
     * 주어진 카테고리 ID에 해당하는 책 목록을 가져와 페이지네이션하여 반환<br>
     * 각 책에 대한 작가 정보를 가져와서 책 상세 응답에 추가
     *
     * @param categoryId 요청된 카테고리의 ID
     * @param pageable 페이지 요청 정보
     * @return 페이지네이션된 책 상세 응답 객체
     * @throws CategoryNotFoundException 주어진 카테고리 ID가 존재하지 않을 경우 발생
     */
    @Transactional(readOnly = true)
    public PageResponse<BookDetailResponse> getBooksByCategoryId(Integer categoryId, Pageable pageable) {

        // 주어진 카테고리 ID가 존재하는지 확인
        if(!categoryRepository.existsById(categoryId)) {
            throw new CategoryNotFoundException();
        }

        // 요청 카테고리 ID에 해당하는 자식 카테고리 조회
        List<Category> childCategoryList = categoryRepository.getChildCategoriesById(categoryId);

        // 자식 카테고리 ID와 요청된 카테고리 ID를 모두 포함하는 리스트를 생성
        List<Integer> targetCategoryIdList = childCategoryList.stream()
                .map(Category::getCategoryId)
                .collect(Collectors.toList());
        targetCategoryIdList.add(categoryId);

        // 카테고리 ID 목록을 이용하여 해당하는 책 목록을 페이지네이션하여 가져옴
        Page<BookDetailResponse> bookDetailResponsePage = bookCategoryRepository.getBooksByCategoryIds(targetCategoryIdList, pageable);

            // 페이지에서 가져온 책 목록의 ID를 추출
            List<Long> bookIdList = bookDetailResponsePage.getContent()
                    .stream()
                    .map(BookDetailResponse::getId)
                    .collect(Collectors.toList());

        // 책 ID 목록을 이용하여 해당하는 작가 정보를 가져옴
        List<ParticipantRoleRegistrationDtoByBookId> participantDtoList = bookRepository.getBookParticipantDtoListByIdList(bookIdList);

        // 각 책에 대한 작가 정보를 책 상세 응답에 추가
        List<BookDetailResponse> responses = bookDetailResponsePage.getContent().stream()
                .map(bookDetail -> {
                    // 책에 대한 작가 목록을 가져옴
                    List<ParticipantRoleRegistrationDto> participantList = participantDtoList.stream()
                            .filter(author -> author.getBookId().equals(bookDetail.getId()))
                            .findFirst()
                            .map(ParticipantRoleRegistrationDtoByBookId::getParticipantList)
                            .orElse(Collections.emptyList());
                    // 책 상세 응답에 작가 목록을 설정
                    bookDetail.setParticipantList(participantList);
                    return bookDetail;
                }).collect(Collectors.toList());

        // 페이지네이션된 책 상세 응답 객체를 생성하여 반환
        return PageResponse.<BookDetailResponse>builder()
                .content(responses)
                .pageNo(bookDetailResponsePage.getNumber())
                .pageSize(bookDetailResponsePage.getSize())
                .totalElements(bookDetailResponsePage.getTotalElements())
                .totalPages(bookDetailResponsePage.getTotalPages())
                .last(bookDetailResponsePage.isLast())
                .build();
    }
}
