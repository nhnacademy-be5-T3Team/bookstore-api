package com.t3t.bookstoreapi.category.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.t3t.bookstoreapi.category.model.entity.Category;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static com.t3t.bookstoreapi.category.model.entity.QCategory.category;

@RequiredArgsConstructor
public class CategoryRepositoryCustomImpl implements CategoryRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    /**
     * 주어진 카테고리 ID를 기준으로 해당 카테고리의 자식 카테고리를 재귀적으로 조회
     *
     * @param categoryId 조회할 카테고리의 ID
     * @return 주어진 카테고리 ID의 자식 카테고리 목록
     * @author Yujin-nKim(김유진)
     */
    @Override
    public List<Category> getChildCategoriesById(Integer categoryId) {
        return getChildCategoriesRecursive(categoryId, new ArrayList<>());
    }

    /**
     * 주어진 카테고리 ID를 기준으로 해당 카테고리의 자식 카테고리를 재귀적으로 조회
     *
     * @param categoryId 조회할 카테고리의 ID
     * @param childCategoryList     자식 카테고리를 저장할 리스트
     * @return 주어진 카테고리 ID의 자식 카테고리 목록
     * @author Yujin-nKim(김유진)
     */
    private List<Category> getChildCategoriesRecursive(Integer categoryId, List<Category> childCategoryList) {
        List<Category> childCategories = jpaQueryFactory
                .selectFrom(category)
                .where(category.parentCategory.categoryId.eq(categoryId))
                .fetch();

        for (Category childCategory : childCategories) {
            childCategoryList.add(childCategory);
            getChildCategoriesRecursive(childCategory.getCategoryId(), childCategoryList);
        }

        return childCategoryList;
    }
}
