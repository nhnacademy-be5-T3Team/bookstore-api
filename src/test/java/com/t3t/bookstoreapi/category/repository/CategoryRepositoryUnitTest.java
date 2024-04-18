package com.t3t.bookstoreapi.category.repository;

import com.t3t.bookstoreapi.category.model.entity.Category;
import com.t3t.bookstoreapi.config.DataSourceConfig;
import com.t3t.bookstoreapi.config.DatabasePropertiesConfig;
import com.t3t.bookstoreapi.config.QueryDslConfig;
import com.t3t.bookstoreapi.config.RestTemplateConfig;
import com.t3t.bookstoreapi.keymanager.service.SecretKeyManagerService;
import com.t3t.bookstoreapi.property.SecretKeyManagerProperties;
import com.t3t.bookstoreapi.property.SecretKeyProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * CategoryRepository 단위 테스트 <br>
 * 1. Depth 범위로 카테고리 조회 시 값을 정상적으로 가져오는지 테스트
 *
 * @author Yujin-nKim(김유진)
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({DataSourceConfig.class, DatabasePropertiesConfig.class,
        QueryDslConfig.class, RestTemplateConfig.class,
        SecretKeyManagerService.class, SecretKeyManagerProperties.class, SecretKeyProperties.class})
@ActiveProfiles("test")
class CategoryRepositoryUnitTest {
    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    void init() {
        assertEquals(0, categoryRepository.findAll().size(), "데이터베이스에 카테고리 데이터가 존재합니다.");
    }

    @DisplayName("Depth로 카테고리 목록 조회 테스트")
    @Test
    void testFindByDepthBetween() {

        // 더미 데이터 setting
        // 1계층 카테고리 (최상위)
        Category rootCategory1 = categoryRepository.save(Category.builder()
                .categoryName("rootCategory1").depth(1).build());
        Category rootCategory2 = categoryRepository.save(Category.builder()
                .categoryName("rootCategory2").depth(1).build());

        for(int i = 0; i < 5; i++) {
            // 2계층 카테고리
            Category category = categoryRepository.save(Category.builder()
                    .categoryName("childCategory" + i + "_under_rootCategory1")
                    .parentCategory(rootCategory1).depth(2).build());

            // 3계층 카테고리
            categoryRepository.save(Category.builder()
                    .categoryName("childCategory" + i + "_under_childCategory" + i)
                    .parentCategory(category).depth(3).build());
        }

        // 2계층 카테고리
        for(int i = 0; i < 3; i++) {
            categoryRepository.save(Category.builder()
                    .categoryName("childCategory" + i + "_under_rootCategory2")
                    .parentCategory(rootCategory2).depth(2).build());
        }

        List<Category> categoryList1 = categoryRepository.findByDepthBetween(1, 1);
        assertEquals(2, categoryList1.size());

        List<Category> categoryList2 = categoryRepository.findByDepthBetween(2, 3);
        assertEquals(13, categoryList2.size());
    }
}
