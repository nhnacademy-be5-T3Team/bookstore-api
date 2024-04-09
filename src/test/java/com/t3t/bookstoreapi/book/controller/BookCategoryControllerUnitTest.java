package com.t3t.bookstoreapi.book.controller;

import com.t3t.bookstoreapi.book.model.response.BookSearchResultResponse;
import com.t3t.bookstoreapi.book.service.BookCategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = BookCategoryController.class)
class BookCategoryControllerUnitTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookCategoryService bookCategoryService;

    @Test
    @DisplayName("특정 카테고리 ID에 해당하는 도서 목록 조회 테스트")
    void testGetBooksByCategoryId() throws Exception {

        int categoryId = 1;

        BookSearchResultResponse bookResponse = BookSearchResultResponse.builder()
                .name("TestBookName").build();

        Page<BookSearchResultResponse> pageResponse = new PageImpl<>(List.of(bookResponse));

        when(bookCategoryService.findBooksByCategoryId(any(), any())).thenReturn(pageResponse);

        mvc.perform(get("/category/{categoryId}/books", categoryId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content[0].name", equalTo(bookResponse.getName())));
    }
}
