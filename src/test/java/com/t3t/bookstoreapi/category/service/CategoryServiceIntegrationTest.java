package com.t3t.bookstoreapi.category.service;

import com.t3t.bookstoreapi.category.model.entity.Category;
import com.t3t.bookstoreapi.category.model.response.CategoryTreeResponse;
import com.t3t.bookstoreapi.category.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * CategoryService 통합 테스트 <br>
 * 1. 카테고리 목록 조회시 루트 노드들이 같은 depth에 있는 경우 올바른 트리가 만들어지는지 테스트
 * 2. 카테고리 목록 조회시 루트 노드들이 다른 depth에 있는 경우 올바른 트리가 만들어지는지 테스트
 *
 * @author Yujin-nKim(김유진)
 */
@SpringBootTest
@Transactional
@ActiveProfiles("test")
class CategoryServiceIntegrationTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    void init() {
        assertEquals(0, categoryRepository.findAll().size(), "데이터베이스에 카테고리 데이터가 존재합니다.");
    }

    @DisplayName("카테고리 전체 목록 계층별 조회 테스트 | 같은 depth에 루트 노드들이 있는 경우 테스트")
    @Test
    void testGetAllCategoriesHierarchy_HavingRootNodesAtSameDepth() {

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

        entityManager.clear();

        List<CategoryTreeResponse> categoryList = categoryService.getCategoryTreeByDepth(1,3);

        assertEquals(2, categoryList.size());
        assertEquals(5, categoryList.get(0).getChildren().size());
        assertEquals(3, categoryList.get(1).getChildren().size());
        assertEquals(1, categoryList.get(0).getChildren().get(0).getChildren().size());
        assertEquals(0, categoryList.get(1).getChildren().get(0).getChildren().size());
    }

    @DisplayName("카테고리 전체 목록 계층별 조회 테스트 | 다른 depth에 루트 노드들이 있는 경우 테스트")
    @Test
    void testGetAllCategoriesHierarchy_testHavingRootNodesAtDifferentDepth() {
        // 더미 데이터 setting
        // 1계층 카테고리 (최상위)
        Category rootCategory1 = categoryRepository.save(Category.builder()
                .categoryName("rootCategory1").depth(1).build());

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

        for(int i = 0; i < 3; i++) {
            // 2계층 카테고리 (최상위)
            Category category = categoryRepository.save(Category.builder()
                    .categoryName("rootCategory" + i)
                    .parentCategory(null).depth(2).build());

            // 3계층 카테고리
            categoryRepository.save(Category.builder()
                    .categoryName("childCategory" + i + "_under_rootCategory" + i)
                    .parentCategory(category).depth(3).build());
        }

        List<CategoryTreeResponse> categoryList = categoryService.getCategoryTreeByDepth(1,3);

        assertEquals(4, categoryList.size());
        assertEquals(5, categoryList.get(0).getChildren().size());
        assertEquals(1, categoryList.get(1).getChildren().size());

    }
}
