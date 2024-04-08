package com.t3t.bookstoreapi.category.repository;

import com.t3t.bookstoreapi.category.model.entity.Category;

import java.util.List;

public interface CategoryRepositoryCustom {
    List<Category> getChildCategoriesById(Integer categoryId);
}
