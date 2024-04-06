package com.t3t.bookstoreapi.category.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.t3t.bookstoreapi.category.model.entity.Category;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static com.t3t.bookstoreapi.category.model.entity.QCategory.category;

@RequiredArgsConstructor
public class CategoryRepositoryImpl implements CategoryRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Category> getChildCategoriesById(Integer categoryId) {
        return getChildCategoriesRecursive(categoryId, new ArrayList<>());
    }

    private List<Category> getChildCategoriesRecursive(Integer categoryId, List<Category> result) {
        List<Category> childCategories = jpaQueryFactory
                .selectFrom(category)
                .where(category.parentCategoryId.eq(categoryId))
                .fetch();

        for (Category childCategory : childCategories) {
            result.add(childCategory);
            getChildCategoriesRecursive(childCategory.getCategoryId(), result);
        }

        return result;
    }
}
