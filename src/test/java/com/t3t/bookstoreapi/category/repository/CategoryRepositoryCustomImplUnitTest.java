package com.t3t.bookstoreapi.category.repository;

import com.t3t.bookstoreapi.category.model.entity.Category;
import com.t3t.bookstoreapi.config.DataSourceConfig;
import com.t3t.bookstoreapi.config.DatabasePropertiesConfig;
import com.t3t.bookstoreapi.config.QueryDslConfig;
import com.t3t.bookstoreapi.config.RestTemplateConfig;
import com.t3t.bookstoreapi.keymanager.service.SecretKeyManagerService;
import com.t3t.bookstoreapi.property.SecretKeyManagerProperties;
import com.t3t.bookstoreapi.property.SecretKeyProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * CategoryRepositoryCustomImpl 단위 테스트 <br>
 * 1. 요청 하위 카테고리의 하위 자식 카테고리를 모두 가져오는지 테스트
 *
 * @author Yujin-nKim(김유진)
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({DataSourceConfig.class, DatabasePropertiesConfig.class,
        QueryDslConfig.class, RestTemplateConfig.class,
        SecretKeyManagerService.class, SecretKeyManagerProperties.class, SecretKeyProperties.class})
@ActiveProfiles("test")
class CategoryRepostioryUnitTest {
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("특정 카테고리 밑의 하위 카테고리를 모두 반환하는지 테스트")
    void testGetChildCategoriesById() {
        Category rootCategory = categoryRepository.save(Category.builder()
                .parentCategory(null)
                .categoryId(1)
                .categoryName("최상단 카테고리")
                .depth(1)
                .build());

        Category level1Category = categoryRepository.save(Category.builder()
                .parentCategory(rootCategory)
                .categoryId(2)
                .categoryName("1계층 카테고리")
                .depth(2)
                .build());

        Category level2Category = categoryRepository.save(Category.builder()
                .parentCategory(level1Category)
                .categoryId(3)
                .categoryName("2계층 카테고리")
                .depth(3)
                .build());

        List<Category> categoryList = categoryRepository.getChildCategoriesById(rootCategory.getCategoryId());

        assertTrue(categoryList.contains(level1Category));
        assertTrue(categoryList.contains(level2Category));
    }
}