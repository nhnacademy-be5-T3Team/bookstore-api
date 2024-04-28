package com.t3t.bookstoreapi.category.service;

import com.t3t.bookstoreapi.category.model.entity.Category;
import com.t3t.bookstoreapi.category.model.response.CategoryTreeResponse;
import com.t3t.bookstoreapi.category.repository.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * {@link CategoryService} 클래스의 단위 테스트
 *
 * @author Yujin-nKim(김유진)
 */
@ExtendWith(MockitoExtension.class)
class CategoryServiceUnitTest {
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Order(1)
    @DisplayName("카테고리 목록 조회 테스트")
    @Test
    public void getCategoryTreeByDepth_ReturnsCorrectTree() {

        List<Category> dummyCategoryList = new ArrayList<>();

        // 1계층 카테고리 (최상위)
        Category rootCategory1 = Category.builder().categoryId(1).categoryName("rootCategory1").depth(1).build();
        Category rootCategory2 = Category.builder().categoryId(2).categoryName("rootCategory2").depth(1).build();
        dummyCategoryList.add(rootCategory1);
        dummyCategoryList.add(rootCategory2);

        for(int i = 0; i < 5; i++) {
            // 2계층 카테고리
            Category category = Category.builder()
                    .categoryId(3 + i)
                    .categoryName("childCategory" + i + "_under_rootCategory1")
                    .parentCategory(rootCategory1).depth(2).build();
            dummyCategoryList.add(category);

            // 3계층 카테고리
            dummyCategoryList.add(Category.builder()
                    .categoryId(8 + i)
                    .categoryName("childCategory" + i + "_under_childCategory" + i)
                    .parentCategory(category).depth(3).build());
        }

        // 2계층 카테고리
        for(int i = 0; i < 3; i++) {
            dummyCategoryList.add(Category.builder()
                    .categoryId(13 + i)
                    .categoryName("childCategory" + i + "_under_rootCategory2")
                    .parentCategory(rootCategory2).depth(2).build());
        }

        when(categoryRepository.findByDepthBetween(anyInt(), anyInt())).thenReturn(dummyCategoryList);

        List<CategoryTreeResponse> result = categoryService.getCategoryTreeByDepth(1, 3);

        assertEquals(2, result.size());
        assertEquals(5, result.get(0).getChildren().size());
        assertEquals(3, result.get(1).getChildren().size());
        assertEquals(1, result.get(0).getChildren().get(0).getChildren().size());
        assertEquals(0, result.get(1).getChildren().get(0).getChildren().size());
    }
}