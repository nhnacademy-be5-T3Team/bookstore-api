package com.t3t.bookstoreapi.review.controller;

import com.t3t.bookstoreapi.model.response.PageResponse;
import com.t3t.bookstoreapi.review.model.response.ReviewResponse;
import com.t3t.bookstoreapi.review.service.ReviewService;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * {@link ReviewController} 클래스의 단위 테스트
 *
 * @author Yujin-nKim(김유진)
 */
@RunWith(SpringRunner.class)
@WebMvcTest(value = ReviewController.class)
public class ReviewControllerUnitTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ReviewService reviewService;

    @DisplayName("도서별 리뷰 목록 조회 테스트")
    @Test
    public void testFindReviewsByBookId() throws Exception {

        Long bookId = 1L;

        PageResponse<ReviewResponse> dummyResponse = PageResponse.<ReviewResponse>builder()
                        .content(Collections.singletonList(ReviewResponse.builder().name("test").build()))
                        .pageNo(0)
                        .pageSize(10)
                        .totalElements(1)
                        .totalPages(1)
                        .last(true)
                        .build();

        when(reviewService.findReviewsByBookId(any(), any())).thenReturn(dummyResponse);

        mockMvc.perform(get("/reviews/books/{bookId}", bookId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists());
    }

    @DisplayName("도서별 리뷰 목록 조회 테스트 | 리뷰 목록이 없는 경우 204 status code 반환 테스트")
    @Test
    public void testFindReviewsByBookId_BookNotFound_ReturnsNoContent() throws Exception {

        Long bookId = 1L;

        when(reviewService.findReviewsByBookId(any(), any())).thenReturn(
                PageResponse.<ReviewResponse>builder()
                        .content(Collections.emptyList())
                        .pageSize(0)
                        .totalElements(0)
                        .totalPages(0)
                        .last(true)
                        .build());

        mockMvc.perform(get("/reviews/books/{bookId}", bookId))
                .andExpect(status().isNoContent());
    }
}
