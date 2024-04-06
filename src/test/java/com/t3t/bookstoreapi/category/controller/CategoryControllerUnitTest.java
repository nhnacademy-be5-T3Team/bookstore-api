package com.t3t.bookstoreapi.category.controller;

import com.t3t.bookstoreapi.category.model.dto.CategoryDto;
import com.t3t.bookstoreapi.category.model.response.CategoryListResponse;
import com.t3t.bookstoreapi.category.service.CategoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = CategoryController.class)
class CategoryControllerUnitTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private CategoryService categoryService;

    @Test
    @DisplayName("카테고리 전체 목록 조회 서비스 테스트")
    void testGetCategoriesHierarchy() throws Exception {

        CategoryDto parentCategory = CategoryDto.builder().id(1).name("가정/요리/뷰티").build();
        CategoryDto childCategory = CategoryDto.builder().id(2).name("가계부").build();

        List<CategoryDto> childCategoryList = new ArrayList<>();
        childCategoryList.add(childCategory);

        CategoryListResponse categoryListResponse = CategoryListResponse.builder()
                .parent(parentCategory)
                .childCategoryList(childCategoryList)
                .build();

        List<CategoryListResponse> categoryListResponses = new ArrayList<>();
        categoryListResponses.add(categoryListResponse);

        when(categoryService.getCategoriesHierarchy()).thenReturn(categoryListResponses);

        mvc.perform(
                MockMvcRequestBuilders.get("/categories")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data[0].parent.name", equalTo(parentCategory.getName())))
                .andExpect(jsonPath("$.data[0].childCategoryList[0].name", equalTo(childCategory.getName())))
                .andExpect(status().isOk());
    }
}
