package com.t3t.bookstoreapi.category.service;

import com.t3t.bookstoreapi.category.model.dto.CategoryDto;
import com.t3t.bookstoreapi.category.model.entity.Category;
import com.t3t.bookstoreapi.category.model.response.CategoryListResponse;
import com.t3t.bookstoreapi.category.repository.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CategoryServiceUnitTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    @DisplayName("카테고리 전체 목록 조회 서비스 테스트")
    void testGetCategoriesHierarchy() {

        Category parentCategory = Category.builder()
                .categoryId(1)
                .parentCategoryId(null)
                .categoryName("가정/요리/뷰티")
                .build();

        Category childCategory = Category.builder()
                .categoryId(2)
                .parentCategoryId(parentCategory.getCategoryId())
                .categoryName("가계부")
                .build();

        List<Category> categoryList = new ArrayList<>();
        categoryList.add(parentCategory);
        categoryList.add(childCategory);

        given(categoryRepository.findAll()).willReturn(categoryList);

        List<CategoryListResponse> result = categoryService.getCategoriesHierarchy();

        assertEquals(1, result.size());
        CategoryListResponse response = result.get(0);
        assertEquals(parentCategory.getCategoryId(), response.getParent().getId());
        assertEquals(parentCategory.getCategoryName(), response.getParent().getName());
        assertEquals(1, response.getChildCategoryList().size());
        CategoryDto childDto = response.getChildCategoryList().get(0);
        assertEquals(childCategory.getCategoryId(), childDto.getId());
        assertEquals(childCategory.getCategoryName(), childDto.getName());
    }
}