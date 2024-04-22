package com.t3t.bookstoreapi.book.controller;

import com.t3t.bookstoreapi.book.exception.BookNotFoundForIdException;
import com.t3t.bookstoreapi.book.model.response.BookDetailResponse;
import com.t3t.bookstoreapi.book.service.BookCategoryService;
import com.t3t.bookstoreapi.category.exception.CategoryNotFoundException;
import com.t3t.bookstoreapi.model.response.PageResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = BookCategoryController.class)
@ActiveProfiles("test")
class BookCategoryControllerUnitTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookCategoryService bookCategoryService;

    @DisplayName("특정 카테고리 ID에 해당하는 도서 목록 조회 테스트")
    @Test
    void testGetBooksByCategoryId() throws Exception {

        Integer categoryId = 1;
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bookId").descending());
        PageResponse<BookDetailResponse> dummyResponse = PageResponse.<BookDetailResponse>builder()
                .content(Collections.singletonList(BookDetailResponse.builder().id(1L).build()))
                .pageNo(0)
                .pageSize(10)
                .totalElements(1)
                .totalPages(1)
                .last(true)
                .build();
        when(bookCategoryService.getBooksByCategoryId(categoryId, pageable)).thenReturn(dummyResponse);

        mockMvc.perform(get("/category/{categoryId}/books", categoryId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists());
    }

    @DisplayName("존재하지 않는 카테고리 식별자로 도서 목록 조회시 예외 발생 테스트")
    @Test
    public void getBookDetailsById_thenReturns404() throws Exception {

        Integer invalidCategoryId = -1;
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bookId").descending());

        given(bookCategoryService.getBooksByCategoryId(invalidCategoryId, pageable)).willThrow(new CategoryNotFoundException());

        mockMvc.perform(MockMvcRequestBuilders.get("/category/{categoryId}/books", invalidCategoryId))
                .andExpect(status().isNotFound());
    }
}
