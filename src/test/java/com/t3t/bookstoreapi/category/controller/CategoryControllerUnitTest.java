package com.t3t.bookstoreapi.category.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.t3t.bookstoreapi.category.model.request.CategoryListRequest;
import com.t3t.bookstoreapi.category.model.response.CategoryTreeResponse;
import com.t3t.bookstoreapi.category.service.CategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * {@link CategoryController} 클래스의 단위 테스트
 *
 * @author Yujin-nKim(김유진)
 */
@RunWith(SpringRunner.class)
@WebMvcTest(value = CategoryController.class)
@ActiveProfiles("test")
class CategoryControllerUnitTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Order(1)
    @DisplayName("카테고리 목록 조회 정상 상황 테스트")
    @Test
    void getCategoryTreeByDepth_Returns200OK_WhenCategoriesExist() throws Exception {

        List<CategoryTreeResponse> dummyResponse = Arrays.asList(
                CategoryTreeResponse.builder()
                        .categoryId(1)
                        .categoryName("rootCategory1")
                        .depth(1)
                        .children(null)
                        .build(),
                CategoryTreeResponse.builder()
                        .categoryId(2)
                        .categoryName("rootCategory2")
                        .depth(1)
                        .children(null)
                        .build()
        );

        CategoryListRequest request = CategoryListRequest.builder().startDepth(1).maxDepth(2).build();

        when(categoryService.getCategoryTreeByDepth(anyInt(), anyInt()))
                .thenReturn(dummyResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists());
    }

    @Order(2)
    @DisplayName("카테고리 목록 조회 테스트 - 데이터가 없는 경우 204 status code 반환하는지 테스트")
    @Test
    void getCategoryTreeByDepth_Returns204NoContent_WhenCategoriesDoNotExist() throws Exception {

        CategoryListRequest request = CategoryListRequest.builder().startDepth(1).maxDepth(2).build();

        when(categoryService.getCategoryTreeByDepth(anyInt(), anyInt())).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("등록된 카테고리가 없습니다"));
    }

    @Order(3)
    @DisplayName("카테고리 목록 조회 비정상황 테스트 - request 유효성 검증 | null 값이 있는 경우")
    @Test
    void getCategoryTreeByDepth_Returns400BadRequest_WhenInvalidRequest_HavingNullValue() throws Exception {

        CategoryListRequest invalidRequest = CategoryListRequest.builder().startDepth(null).maxDepth(null).build();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        byte[] contentBytes = result.getResponse().getContentAsByteArray();
        JsonNode responseBodyJson = objectMapper.readTree(contentBytes);

        assertTrue(responseBodyJson.has("message"));
        assertTrue(responseBodyJson.get("message").asText().contains("startDepth는 null일 수 없습니다."));
        assertTrue(responseBodyJson.get("message").asText().contains("maxDepth는 null일 수 없습니다."));
    }

    @Order(4)
    @DisplayName("카테고리 목록 조회 비정상황 테스트 - request 유효성 검증 | 1이상의 값이 아닌 경우")
    @Test
    void getCategoryTreeByDepth_Returns400BadRequest_WhenInvalidRequest_HavingMinusValue() throws Exception {

        CategoryListRequest invalidRequest = CategoryListRequest.builder().startDepth(-1).maxDepth(1).build();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        byte[] contentBytes = result.getResponse().getContentAsByteArray();
        JsonNode responseBodyJson = objectMapper.readTree(contentBytes);

        assertTrue(responseBodyJson.has("message"));
        assertTrue(responseBodyJson.get("message").asText().contains("startDepth는 1이상의 값이어야 합니다"));
    }

    @Order(5)
    @DisplayName("카테고리 목록 조회 비정상황 테스트 - request 유효성 검증 | startDepth가 maxDepth보다 큰 경우")
    @Test
    void getCategoryTreeByDepth_Returns400BadRequest_WhenInvalidRequest_HavingInvalidRange() throws Exception {

        CategoryListRequest invalidRequest = CategoryListRequest.builder().startDepth(2).maxDepth(1).build();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        byte[] contentBytes = result.getResponse().getContentAsByteArray();
        JsonNode responseBodyJson = objectMapper.readTree(contentBytes);

        assertTrue(responseBodyJson.has("message"));
        assertTrue(responseBodyJson.get("message").asText().contains("startDepth는 maxDepth보다 같거나 작아야 합니다."));
    }
}
