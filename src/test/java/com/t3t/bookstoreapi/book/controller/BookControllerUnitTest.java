package com.t3t.bookstoreapi.book.controller;

import com.t3t.bookstoreapi.book.exception.BookNotFoundForIdException;
import com.t3t.bookstoreapi.book.model.response.BookDetailResponse;
import com.t3t.bookstoreapi.book.service.BookService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = BookController.class)
@ActiveProfiles("test")
class BookControllerUnitTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @DisplayName("도서 상세 조회 테스트")
    @Test
    void getBookDetailsById_thenReturns200() throws Exception {
        Long bookId = 1L;
        BookDetailResponse dummyResponse = BookDetailResponse.builder()
                        .id(1L).build();
        when(bookService.getBookDetailsById(any())).thenReturn(dummyResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/books/{bookId}", bookId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists());

    }

    @DisplayName("존재하지 않는 도서 식별자로 도서 상세 조회 테스트")
    @Test
    public void getBookDetailsById_thenReturns404() throws Exception {

        Long invalidBookId = -1L;
        given(bookService.getBookDetailsById(invalidBookId)).willThrow(new BookNotFoundForIdException(any()));

        mockMvc.perform(MockMvcRequestBuilders.get("/books/{bookId}", invalidBookId))
                .andExpect(status().isNotFound());
    }
}
