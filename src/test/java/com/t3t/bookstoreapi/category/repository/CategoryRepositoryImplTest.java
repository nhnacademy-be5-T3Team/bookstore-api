package com.t3t.bookstoreapi.category.repository;

import com.t3t.bookstoreapi.category.model.entity.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
@ActiveProfiles("prod")
class CategoryRepostioryUnitTest {
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("특정 카테고리 밑의 하위 카테고리를 모두 반환하는지 테스트")
    void test() {
        Category rootCategory = categoryRepository.save(Category.builder()
                .parentCategoryId(null)
                .categoryId(1)
                .categoryName("최상단 카테고리")
                .build());

        Category level1Category = categoryRepository.save(Category.builder()
                .parentCategoryId(1)
                .categoryId(2)
                .categoryName("1계층 카테고리")
                .build());

        Category level2Category = categoryRepository.save(Category.builder()
                .parentCategoryId(2)
                .categoryId(3)
                .categoryName("2계층 카테고리")
                .build());

        List<Category> categoryList = categoryRepository.getChildCategoriesById(rootCategory.getCategoryId());

        assertTrue(categoryList.contains(level1Category));
        assertTrue(categoryList.contains(level2Category));
    }
}