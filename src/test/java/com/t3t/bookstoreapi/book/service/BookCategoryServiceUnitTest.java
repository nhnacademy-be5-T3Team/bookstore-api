package com.t3t.bookstoreapi.book.service;


import com.t3t.bookstoreapi.book.model.dto.ParticipantRoleRegistrationDtoByBookId;
import com.t3t.bookstoreapi.book.model.response.BookDetailResponse;
import com.t3t.bookstoreapi.book.repository.BookCategoryRepository;
import com.t3t.bookstoreapi.book.repository.BookRepository;
import com.t3t.bookstoreapi.category.exception.CategoryNotFoundException;
import com.t3t.bookstoreapi.category.model.entity.Category;
import com.t3t.bookstoreapi.category.repository.CategoryRepository;
import com.t3t.bookstoreapi.model.response.PageResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookCategoryServiceUnitTest {
    @Mock
    private BookRepository bookRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private BookCategoryRepository bookCategoryRepository;
    @InjectMocks
    private BookCategoryService bookCategoryService;

    @Test
    @DisplayName("특정 카테고리 ID에 해당하는 도서 목록 조회 테스트")
    void testGetBooksByCategoryId() {

        // dummy data setting
        Integer categoryId = 1;
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bookId").descending());

        when(categoryRepository.existsById(categoryId)).thenReturn(true);
        List<Category> childCategories = Arrays.asList(Category.builder().categoryId(2).build());
        when(categoryRepository.getChildCategoriesById(categoryId)).thenReturn(childCategories);

        List<Integer> targetCategoryIdList = Arrays.asList(2, 1);
        Page<BookDetailResponse> bookDetailResponsePage = new PageImpl<>(Collections.emptyList());
        when(bookCategoryRepository.getBooksByCategoryIds(targetCategoryIdList, pageable)).thenReturn(bookDetailResponsePage);

        List<ParticipantRoleRegistrationDtoByBookId> participantDtoList = Collections.singletonList(ParticipantRoleRegistrationDtoByBookId.builder().build());
        when(bookRepository.getBookParticipantDtoListByIdList(any())).thenReturn(participantDtoList);

        PageResponse<BookDetailResponse> result = bookCategoryService.getBooksByCategoryId(categoryId, pageable);

        assertNotNull(result);
        assertEquals(0, result.getContent().size());
        assertEquals(0, result.getPageNo());
        assertEquals(0, result.getTotalElements());
        assertEquals(1, result.getTotalPages());

        verify(categoryRepository, times(1)).existsById(categoryId);
        verify(categoryRepository, times(1)).getChildCategoriesById(categoryId);
        verify(bookCategoryRepository, times(1)).getBooksByCategoryIds(targetCategoryIdList, pageable);
        verify(bookRepository, times(1)).getBookParticipantDtoListByIdList(any());
    }

    @Test
    @DisplayName("특정 카테고리 ID에 해당하는 도서가 없는 경우 예외 발생 테스트")
    void testFindBooksByCategoryId_BookNotFoundForCategoryIdException() {
        Integer categoryId = 1;
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bookId").descending());

        when(categoryRepository.existsById(categoryId)).thenReturn(false);

        assertThrows(CategoryNotFoundException.class, () -> {
            bookCategoryService.getBooksByCategoryId(categoryId, pageable);
        });

        verify(categoryRepository, times(1)).existsById(categoryId);
        verify(categoryRepository, never()).getChildCategoriesById(anyInt());
        verify(bookCategoryRepository, never()).getBooksByCategoryIds(anyList(), any());
        verify(bookRepository, never()).getBookParticipantDtoListByIdList(any());
    }
}
