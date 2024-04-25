package com.t3t.bookstoreapi.category.repository;

import com.t3t.bookstoreapi.category.model.entity.Category;

import java.util.List;

public interface CategoryRepositoryCustom {
    /**
     * 주어진 카테고리 ID를 기준으로 자식 카테고리를 조회
     *
     * @param categoryId 조회할 카테고리의 ID
     * @return 주어진 카테고리 ID의 자식 카테고리 목록
     * @author Yujin-nKim(김유진)
     */
    List<Category> getChildCategoriesById(Integer categoryId);
}
